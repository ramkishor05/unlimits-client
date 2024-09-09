/**
 * 
 */
package com.brijframework.client.device.service;

import static com.brijframework.client.constants.Constants.CUST_BUSINESS_APP;
import static com.brijframework.client.constants.Constants.INVALID_CLIENT;
import static com.brijframework.client.constants.Constants.MY_UNLIMITS;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
import org.brijframework.util.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.device.mapper.DeviceUnlimitsImageItemMapper;
import com.brijframework.client.device.mapper.DeviceUnlimitsImageMapper;
import com.brijframework.client.device.mapper.DeviceUnlimitsVisualizeMapper;
import com.brijframework.client.device.model.UIDeviceUnlimitsImage;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOUnlimitsImage;
import com.brijframework.client.entities.EOUnlimitsImageItem;
import com.brijframework.client.entities.EOUnlimitsVisualize;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.repository.CustBusinessAppRepository;
import com.brijframework.client.repository.UnlimitsImageItemRepository;
import com.brijframework.client.repository.UnlimitsImageRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceUnlimitsImageServiceImpl extends CrudServiceImpl<UIDeviceUnlimitsImage, EOUnlimitsImage, Long>
		implements DeviceUnlimitsImageService {

	@Autowired
	private CustBusinessAppRepository custBusinessAppRepository;

	@Autowired
	private UnlimitsImageRepository clientUnlimitsImageRepository;

	@Autowired
	private DeviceUnlimitsImageMapper clientUnlimitsImageMapper;

	@Autowired
	private UnlimitsImageItemRepository clientUnlimitsImageItemRepository;

	@Autowired
	private DeviceUnlimitsImageItemMapper clientUnlimitsImageItemMapper;

	@Autowired
	private DeviceUnlimitsVisualizeMapper deviceUnlimitsVisualizeMapper;

	@Override
	public JpaRepository<EOUnlimitsImage, Long> getRepository() {
		return clientUnlimitsImageRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsImage, UIDeviceUnlimitsImage> getMapper() {
		return clientUnlimitsImageMapper;
	}

	{
		CustomPredicate<EOUnlimitsImage> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};

		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}

	@Override
	public void preAdd(UIDeviceUnlimitsImage data, EOUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		if (StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS + maxTransactionId);
			entity.setName(MY_UNLIMITS + maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preAdd(UIDeviceUnlimitsImage data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
	}

	@Override
	public void postAdd(UIDeviceUnlimitsImage data, EOUnlimitsImage entity) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIDeviceUnlimitsImage data, EOUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		if (StringUtil.isEmpty(data.getName())) {
			int maxTransactionId = clientUnlimitsImageRepository.getMaxTransactionId(eoCustBusinessApp.getId());
			data.setName(MY_UNLIMITS + maxTransactionId);
			entity.setName(MY_UNLIMITS + maxTransactionId);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void postUpdate(UIDeviceUnlimitsImage data, EOUnlimitsImage entity, Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		eoCustBusinessApp.setUnlimitsImage(entity);
		custBusinessAppRepository.save(eoCustBusinessApp);
	}

	@Override
	public void merge(UIDeviceUnlimitsImage dtoObject, EOUnlimitsImage entityObject,
			UIDeviceUnlimitsImage updateDtoObject, EOUnlimitsImage updateEntityObject,
			Map<String, List<String>> headers) {
		clientUnlimitsImageItemRepository.deleteByUnlimitsImageId(updateEntityObject.getId());
		if(!CollectionUtils.isEmpty(dtoObject.getImageItems())) {
			List<EOUnlimitsImageItem> imageItems = clientUnlimitsImageItemMapper.mapToDAO(dtoObject.getImageItems());
			imageItems.forEach(item -> item.setUnlimitsImage(updateEntityObject));
			List<EOUnlimitsImageItem> imageItemsReturn = clientUnlimitsImageItemRepository.saveAll(imageItems);
			updateDtoObject.setImageItems(clientUnlimitsImageItemMapper.mapToDTO(imageItemsReturn));
		}
	}

	@Override
	public UIDeviceUnlimitsImage getCurrent(Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		return clientUnlimitsImageMapper.mapToDTO(eoCustBusinessApp.getUnlimitsImage());
	}

	@Override
	public void postFetch(EOUnlimitsImage findObject, UIDeviceUnlimitsImage dtoObject) {
		dtoObject.setType(UnlimitsType.IMAGE);
		List<EOUnlimitsVisualize> eoUnlimitsVisualizes =findObject.getUnlimitsVisualizeList();
		if(!CollectionUtils.isEmpty(eoUnlimitsVisualizes)) {
			Map<Integer, UIDeviceUnlimitsVisualize> unlimitsVisualizeList = eoUnlimitsVisualizes.stream()
					.collect(Collectors.toMap(unlimitsVisualize -> unlimitsVisualize.getVisualizeYear(),
							unlimitsVisualize -> deviceUnlimitsVisualizeMapper.mapToDTO(unlimitsVisualize)));
			dtoObject.setVisualizeMap(unlimitsVisualizeList);
		}
	}

	@Override
	public List<EOUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable,
			Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, pageable, filters);
	}

	@Override
	public List<EOUnlimitsImage> repositoryFindAll(Map<String, List<String>> headers, Sort sort,
			Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
