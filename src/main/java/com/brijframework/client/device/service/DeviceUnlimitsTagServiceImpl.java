/**
 * 
 */
package com.brijframework.client.device.service;

import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.MY_UNLIMITS;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.util.text.StringUtil;
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
import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.device.mapper.DeviceUnlimitsTagItemMapper;
import com.brijframework.client.device.mapper.DeviceUnlimitsTagMapper;
import com.brijframework.client.device.mapper.DeviceUnlimitsVisualizeMapper;
import com.brijframework.client.device.model.UIDeviceUnlimitsTag;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOUnlimitsTag;
import com.brijframework.client.entities.EOUnlimitsTagItem;
import com.brijframework.client.entities.EOUnlimitsVisualize;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.UnlimitsTagItemRepository;
import com.brijframework.client.repository.UnlimitsTagRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceUnlimitsTagServiceImpl extends CrudServiceImpl<UIDeviceUnlimitsTag, EOUnlimitsTag, Long>
		implements DeviceUnlimitsTagService {

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private UnlimitsTagRepository clientUnlimitsTagRepository;

	@Autowired
	private DeviceUnlimitsTagMapper clientUnlimitsTagMapper;

	@Autowired
	private UnlimitsTagItemRepository clientUnlimitsTagItemRepository;

	@Autowired
	private DeviceUnlimitsTagItemMapper clientUnlimitsTagItemMapper;

	@Autowired
	private DeviceUnlimitsVisualizeMapper deviceUnlimitsVisualizeMapper;

	@Override
	public JpaRepository<EOUnlimitsTag, Long> getRepository() {
		return clientUnlimitsTagRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsTag, UIDeviceUnlimitsTag> getMapper() {
		return clientUnlimitsTagMapper;
	}

	{
		CustomPredicate<EOUnlimitsTag> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};

		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
	}
	
	@Override
	public void preAdd(UIDeviceUnlimitsTag data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void preAdd(UIDeviceUnlimitsTag data, EOUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		if (StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS + maxTransactionId);
			entity.setName(MY_UNLIMITS + maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void postAdd(UIDeviceUnlimitsTag data, EOUnlimitsTag entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIDeviceUnlimitsTag data, EOUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		if (StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsTagRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS + maxTransactionId);
			entity.setName(MY_UNLIMITS + maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void postUpdate(UIDeviceUnlimitsTag data, EOUnlimitsTag entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		eoCustBusinessApp.setUnlimitsTag(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}

	@Override
	public void merge(UIDeviceUnlimitsTag dtoObject, EOUnlimitsTag entityObject, UIDeviceUnlimitsTag updateDtoObject,
			EOUnlimitsTag updateEntityObject, Map<String, List<String>> headers) {
		List<EOUnlimitsTagItem> mindSetItems = clientUnlimitsTagItemMapper.mapToDAO(dtoObject.getTagItems());
		mindSetItems.forEach(item -> item.setUnlimitsTag(updateEntityObject));
		List<EOUnlimitsTagItem> tagItemsReturn = clientUnlimitsTagItemRepository.saveAll(mindSetItems);
		updateDtoObject.setTagItems(clientUnlimitsTagItemMapper.mapToDTO(tagItemsReturn));
	}

	@Override
	public UIDeviceUnlimitsTag getCurrent(Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientUnlimitsTagMapper.mapToDTO(eoCustBusinessApp.getUnlimitsTag());
	}

	@Override
	public void postFetch(EOUnlimitsTag findObject, UIDeviceUnlimitsTag dtoObject) {
		dtoObject.setType(UnlimitsType.WORDS);
		List<EOUnlimitsVisualize> eoUnlimitsVisualizes = findObject.getUnlimitsVisualizeList();
		Map<Integer, UIDeviceUnlimitsVisualize> unlimitsVisualizeList = eoUnlimitsVisualizes.stream()
				.collect(Collectors.toMap(unlimitsVisualize -> unlimitsVisualize.getVisualizeYear(),
						unlimitsVisualize -> deviceUnlimitsVisualizeMapper.mapToDTO(unlimitsVisualize)));
		dtoObject.setVisualizeMap(unlimitsVisualizeList);
	}

	@Override
	public List<EOUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable,
			Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, pageable, filters);
	}

	@Override
	public List<EOUnlimitsTag> repositoryFindAll(Map<String, List<String>> headers, Sort sort,
			Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
