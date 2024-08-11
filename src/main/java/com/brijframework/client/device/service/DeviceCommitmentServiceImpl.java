/**
 * 
 */
package com.brijframework.client.device.service;
import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.DEVICE_DATE_FORMAT_MMMM_DD_YYYY;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;

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

import com.brijframework.client.device.mapper.DeviceCommitmentGroupMapper;
import com.brijframework.client.device.mapper.DeviceCommitmentItemMapper;
import com.brijframework.client.device.model.UIDeviceCommitmentGroup;
import com.brijframework.client.entities.EOCommitmentGroup;
import com.brijframework.client.entities.EOCommitmentItem;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.CommitmentGroupRepository;
import com.brijframework.client.repository.CommitmentItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceCommitmentServiceImpl
		extends CrudServiceImpl<UIDeviceCommitmentGroup, EOCommitmentGroup, Long>
		implements DeviceCommitmentService {

	private static final String COMMITMENT_DATE = "commitmentDate";

	@Autowired
	private CommitmentGroupRepository clientCommitmentGroupRepository;

	@Autowired
	private DeviceCommitmentGroupMapper clientCommitmentGroupMapper;

	@Autowired
	private CommitmentItemRepository clientCommitmentItemRepository;

	@Autowired
	private DeviceCommitmentItemMapper clientCommitmentItemMapper;

	@Override
	public JpaRepository<EOCommitmentGroup, Long> getRepository() {
		return clientCommitmentGroupRepository;
	}

	@Override
	public GenericMapper<EOCommitmentGroup, UIDeviceCommitmentGroup> getMapper() {
		return clientCommitmentGroupMapper;
	}
	
	{
		CustomPredicate<EOCommitmentGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOCommitmentGroup> commitmentDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> commitmentDatePath = root.get(COMMITMENT_DATE);
			In<Object> commitmentDateIn = criteriaBuilder.in(commitmentDatePath);
			DateFormat timeFormat = new SimpleDateFormat(DEVICE_DATE_FORMAT_MMMM_DD_YYYY);
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
	public void preAdd(UIDeviceCommitmentGroup data, EOCommitmentGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIDeviceCommitmentGroup data, EOCommitmentGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIDeviceCommitmentGroup dtoObject, EOCommitmentGroup entityObject,
			UIDeviceCommitmentGroup updateDtoObject, EOCommitmentGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOCommitmentItem> commitmentItems = clientCommitmentItemMapper.mapToDAO(dtoObject.getCommitments());
		commitmentItems.forEach(item -> item.setGroup(updateEntityObject));
		List<EOCommitmentItem> commitmentItemsReturn = clientCommitmentItemRepository.saveAll(commitmentItems);
		updateDtoObject.setCommitments(clientCommitmentItemMapper.mapToDTO(commitmentItemsReturn));
	}

	@Override
	public List<EOCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}