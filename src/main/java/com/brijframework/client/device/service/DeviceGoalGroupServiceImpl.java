/**
 * 
 */
package com.brijframework.client.device.service;
import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.UI_DATE_FORMAT_MM_DD_YY;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceGoalGroupMapper;
import com.brijframework.client.device.mapper.DeviceGoalItemMapper;
import com.brijframework.client.device.model.UIDeviceGoalGroup;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOGoalGroup;
import com.brijframework.client.entities.EOGoalItem;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.GoalGroupRepository;
import com.brijframework.client.repository.GoalItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceGoalGroupServiceImpl extends CrudServiceImpl<UIDeviceGoalGroup, EOGoalGroup, Long>
		implements DeviceGoalGroupService {
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceGoalGroupServiceImpl.class);

	private static final String GOAL_DATE = "goalDate";

	@Autowired
	private GoalGroupRepository clientGoalGroupRepository;

	@Autowired
	private DeviceGoalGroupMapper clientGoalGroupMapper;
	
	@Autowired
	private GoalItemRepository clientGoalItemRepository;

	@Autowired
	private DeviceGoalItemMapper clientGoalItemMapper;

	@Override
	public JpaRepository<EOGoalGroup, Long> getRepository() {
		return clientGoalGroupRepository;
	}

	@Override
	public GenericMapper<EOGoalGroup, UIDeviceGoalGroup> getMapper() {
		return clientGoalGroupMapper;
	}
	
	{
		CustomPredicate<EOGoalGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
				In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
				custBusinessAppIn.value(filter.getColumnValue());
				return custBusinessAppIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for custBusinessApp: " + filter.getColumnValue(), e);
				return null;
			}
		};
		
		CustomPredicate<EOGoalGroup> goalDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Date> goalDatePath = root.get(GOAL_DATE);
				In<Object> goalDateIn = criteriaBuilder.in(goalDatePath);
				DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MM_DD_YY);
				Date date = timeFormat.parse(filter.getColumnValue().toString());
				goalDateIn.value(new java.sql.Date(date.getTime()) );
				return goalDateIn;
			} catch (ParseException e) {
				LOGGER.error("WARN: unexpected parse exception for goalDate: " + filter.getColumnValue(), e);
				return null;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for goalDate: " + filter.getColumnValue());
				return null;
			}
		};
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(GOAL_DATE, goalDate);
	}
	

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}
	
	@Override
	public void preAdd(UIDeviceGoalGroup data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIDeviceGoalGroup data, EOGoalGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIDeviceGoalGroup data, EOGoalGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIDeviceGoalGroup dtoObject, EOGoalGroup entityObject, UIDeviceGoalGroup updateDtoObject,
			EOGoalGroup updateEntityObject, Map<String, List<String>> headers) {
		List<EOGoalItem> goalItems = clientGoalItemMapper.mapToDAO(dtoObject.getGoals());
		goalItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOGoalItem> goalItemsReturn = clientGoalItemRepository.saveAll(goalItems);
		updateDtoObject.setGoals(clientGoalItemMapper.mapToDTO(goalItemsReturn));
	}

	@Override
	public List<EOGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
