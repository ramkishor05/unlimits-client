/**
 * 
 */
package com.brijframework.client.unlimits.device.service;
import static com.brijframework.client.constants.ClientConstants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.ClientConstants.INVALID_CLIENT;
import static com.brijframework.client.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientGoalGroupMapper;
import com.brijframework.client.mapper.ClientGoalItemMapper;
import com.brijframework.client.repository.ClientGoalGroupRepository;
import com.brijframework.client.repository.ClientGoalItemRepository;
import com.brijframework.client.unlimits.entities.EOClientGoalGroup;
import com.brijframework.client.unlimits.entities.EOClientGoalItem;
import com.brijframework.client.unlimits.model.UIClientGoalGroup;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientGoalGroupServiceImpl extends CrudServiceImpl<UIClientGoalGroup, EOClientGoalGroup, Long>
		implements DeviceClientGoalGroupService {

	private static final String GOAL_DATE = "goalDate";

	@Autowired
	private ClientGoalGroupRepository clientGoalGroupRepository;

	@Autowired
	private ClientGoalGroupMapper clientGoalGroupMapper;
	
	@Autowired
	private ClientGoalItemRepository clientGoalItemRepository;

	@Autowired
	private ClientGoalItemMapper clientGoalItemMapper;

	@Override
	public JpaRepository<EOClientGoalGroup, Long> getRepository() {
		return clientGoalGroupRepository;
	}

	@Override
	public GenericMapper<EOClientGoalGroup, UIClientGoalGroup> getMapper() {
		return clientGoalGroupMapper;
	}
	
	{
		CustomPredicate<EOClientGoalGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOClientGoalGroup> goalDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> goalDatePath = root.get(GOAL_DATE);
			In<Object> goalDateIn = criteriaBuilder.in(goalDatePath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			goalDateIn.value(new java.sql.Date(date.getTime()) );
			return goalDateIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(GOAL_DATE, goalDate);
	}

	@Override
	public void preAdd(UIClientGoalGroup data, EOClientGoalGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientGoalGroup data, EOClientGoalGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIClientGoalGroup dtoObject, EOClientGoalGroup entityObject, UIClientGoalGroup updateDtoObject,
			EOClientGoalGroup updateEntityObject, Map<String, List<String>> headers) {
		List<EOClientGoalItem> goalItems = clientGoalItemMapper.mapToDAO(dtoObject.getGoals());
		goalItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOClientGoalItem> goalItemsReturn = clientGoalItemRepository.saveAll(goalItems);
		updateDtoObject.setGoals(clientGoalItemMapper.mapToDTO(goalItemsReturn));
	}

	@Override
	public List<EOClientGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
