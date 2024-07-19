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
import com.brijframework.client.mapper.ClientCommitmentGroupMapper;
import com.brijframework.client.mapper.ClientCommitmentItemMapper;
import com.brijframework.client.repository.ClientCommitmentGroupRepository;
import com.brijframework.client.repository.ClientCommitmentItemRepository;
import com.brijframework.client.unlimits.entities.EOClientCommitmentGroup;
import com.brijframework.client.unlimits.entities.EOClientCommitmentItem;
import com.brijframework.client.unlimits.model.UIClientCommitmentGroup;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientCommitmentServiceImpl
		extends CrudServiceImpl<UIClientCommitmentGroup, EOClientCommitmentGroup, Long>
		implements DeviceClientCommitmentService {

	private static final String COMMITMENT_DATE = "commitmentDate";

	@Autowired
	private ClientCommitmentGroupRepository clientCommitmentGroupRepository;

	@Autowired
	private ClientCommitmentGroupMapper clientCommitmentGroupMapper;

	@Autowired
	private ClientCommitmentItemRepository clientCommitmentItemRepository;

	@Autowired
	private ClientCommitmentItemMapper clientCommitmentItemMapper;

	@Override
	public JpaRepository<EOClientCommitmentGroup, Long> getRepository() {
		return clientCommitmentGroupRepository;
	}

	@Override
	public GenericMapper<EOClientCommitmentGroup, UIClientCommitmentGroup> getMapper() {
		return clientCommitmentGroupMapper;
	}
	
	{
		CustomPredicate<EOClientCommitmentGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOClientCommitmentGroup> commitmentDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> commitmentDatePath = root.get(COMMITMENT_DATE);
			In<Object> commitmentDateIn = criteriaBuilder.in(commitmentDatePath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			commitmentDateIn.value(new java.sql.Date(date.getTime()) );
			return commitmentDateIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(COMMITMENT_DATE, commitmentDate);
	}
	

	@Override
	public void preAdd(UIClientCommitmentGroup data, EOClientCommitmentGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientCommitmentGroup data, EOClientCommitmentGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIClientCommitmentGroup dtoObject, EOClientCommitmentGroup entityObject,
			UIClientCommitmentGroup updateDtoObject, EOClientCommitmentGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientCommitmentItem> commitmentItems = clientCommitmentItemMapper.mapToDAO(dtoObject.getCommitments());
		commitmentItems.forEach(item -> item.setGroup(updateEntityObject));
		List<EOClientCommitmentItem> commitmentItemsReturn = clientCommitmentItemRepository.saveAll(commitmentItems);
		updateDtoObject.setCommitments(clientCommitmentItemMapper.mapToDTO(commitmentItemsReturn));
	}

	@Override
	public List<EOClientCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
