/**
 * 
 */
package com.brijframework.client.device.service;

import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.MY_UNLIMITS;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.brijframework.util.text.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;
import org.unlimits.rest.repository.CustomPredicate;

import com.brijframework.client.constants.Constants;
import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.device.mapper.DeviceUnlimitsMapper;
import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceUnlimitsItem;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOUnlimits;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.UnlimitsRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceUnlimitsServiceImpl extends CrudServiceImpl<UIDeviceUnlimits, EOUnlimits, Long>
		implements DeviceUnlimitsService {
	
	private static final String UNLIMITS_TAG_ID = "unlimitsId";

	private static final Logger LOGGER= LoggerFactory.getLogger(DeviceUnlimitsServiceImpl.class);

	private static final String UNLIMITS_ID = "unlimitsId";

	@Autowired
	private UnlimitsRepository clientUnlimitsRepository;

	@Autowired
	private DeviceUnlimitsMapper clientUnlimitsMapper;

	@Autowired
	private DeviceUnlimitsItemService deviceUnlimitsItemService;
	
	@Autowired
	@Qualifier("DeviceUnlimitsVisualizeService")
	private DeviceUnlimitsVisualizeService deviceVisualizeService;

	@Override
	public JpaRepository<EOUnlimits, Long> getRepository() {
		return clientUnlimitsRepository;
	}

	@Override
	public GenericMapper<EOUnlimits, UIDeviceUnlimits> getMapper() {
		return clientUnlimitsMapper;
	}

	{
		CustomPredicate<EOUnlimits> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			try {
				Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
				In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
				custBusinessAppIn.value(filter.getColumnValue());
				return custBusinessAppIn;
			}catch (Exception e) {
				LOGGER.error("WARN: unexpected object for custBusinessApp: " + filter.getColumnValue(), e);
				return null;
			}
		};

		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		
		CustomPredicate<EOUnlimits> unlimitsId = (type, root, criteriaQuery, criteriaBuilder,
				filter) -> {
			try {
				Path<Object> custBusinessAppPath = root.get("id");
				return criteriaBuilder.equal(custBusinessAppPath, Long.valueOf(filter.getColumnValue().toString()));
			} catch (Exception e) {
				LOGGER.error("WARN: unexpected exception for subCategoryId: " + filter.getColumnValue(), e);
				return null;
			}
		};

		addCustomPredicate(UNLIMITS_TAG_ID, unlimitsId);
		
		addCustomPredicate(UNLIMITS_ID, unlimitsId);
		
	}
	
	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}
	
	@Override
	public void preAdd(UIDeviceUnlimits data, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIDeviceUnlimits data, EOUnlimits entity, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		if (StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS + maxTransactionId);
			entity.setName(MY_UNLIMITS + maxTransactionId);
		}
		entity.setType(data.getType().getType());
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIDeviceUnlimits data, EOUnlimits entity, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		if (StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS + maxTransactionId);
			entity.setName(MY_UNLIMITS + maxTransactionId);
		}
		entity.setType(data.getType().getType());
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIDeviceUnlimits dtoObject, EOUnlimits entityObject, UIDeviceUnlimits updateDtoObject,
			EOUnlimits updateEntityObject, 
			Map<String, List<String>> headers, Map<String, Object> filters , Map<String, Object> actions) {
		deviceUnlimitsItemService.deleteByUnlimitsId(entityObject.getId());
		List<UIDeviceUnlimitsItem> tagItems = dtoObject.getItems();
		if(!CollectionUtils.isEmpty(tagItems)) {
			tagItems.forEach(item -> {
				item.setUnlimitsId(updateEntityObject.getId());
				item.setUnlimits(updateEntityObject);
			});
			updateDtoObject.setItems(deviceUnlimitsItemService.saveAll(tagItems, headers, filters, actions));
		}
	}

	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(Constants.RECORD_STATE, RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void postFetch(EOUnlimits findObject, UIDeviceUnlimits dtoObject, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions) {
		dtoObject.setType(UnlimitsType.findByType(findObject.getType()));
		dtoObject.setUnlimitsDate(findObject.getDate());
		filters.put(UNLIMITS_TAG_ID, findObject.getId());
		List<UIDeviceUnlimitsVisualize> eoUnlimitsVisualizes = deviceVisualizeService.findAll(headers, filters);
		if(!CollectionUtils.isEmpty(eoUnlimitsVisualizes)) {
			Map<Integer, UIDeviceUnlimitsVisualize> unlimitsVisualizeList = eoUnlimitsVisualizes.stream().filter(unlimitsVisualize -> unlimitsVisualize.getVisualizeYear()!=null)
					.collect(Collectors.toMap(unlimitsVisualize -> unlimitsVisualize.getVisualizeYear(),
							unlimitsVisualize -> unlimitsVisualize));
			dtoObject.setVisualizeMap(unlimitsVisualizeList);
		}
		dtoObject.setItems(deviceUnlimitsItemService.findAll(headers, filters));
	}

	@Override
	public List<EOUnlimits> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOUnlimits> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable,
			Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, pageable, filters);
	}

	@Override
	public List<EOUnlimits> repositoryFindAll(Map<String, List<String>> headers, Sort sort,
			Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
