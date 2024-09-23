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
import com.brijframework.client.device.mapper.DeviceCommitmentGroupMapper;
import com.brijframework.client.device.mapper.DeviceCommitmentItemMapper;
import com.brijframework.client.device.model.UIDeviceCommitmentGroup;
import com.brijframework.client.entities.EOCommitmentGroup;
import com.brijframework.client.entities.EOCommitmentItem;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
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
	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceUnlimitsTagServiceImpl.class);

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
		
		CustomPredicate<EOCommitmentGroup> commitmentDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Date> commitmentDatePath = root.get(COMMITMENT_DATE);
				In<Object> commitmentDateIn = criteriaBuilder.in(commitmentDatePath);
				DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MM_DD_YY);
				Date date = timeFormat.parse(filter.getColumnValue().toString());
				commitmentDateIn.value(new java.sql.Date(date.getTime()) );
				return commitmentDateIn;
			} catch (ParseException e) {
				LOGGER.error("WARN: unexpected parse exception in commitmentDate: " + filter.getColumnValue(), e);
				return null;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected exception in commitmentDate: " + filter.getColumnValue(), e);
				return null;
			}
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(COMMITMENT_DATE, commitmentDate);
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}

	
	@Override
	public void preAdd(UIDeviceCommitmentGroup data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
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
