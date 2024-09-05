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

import org.brijframework.util.reflect.FieldUtil;
import org.brijframework.util.support.ReflectionAccess;
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
import com.brijframework.client.device.mapper.DeviceMindSetGroupMapper;
import com.brijframework.client.device.mapper.DeviceMindSetItemMapper;
import com.brijframework.client.device.model.UIDeviceMindSetGroup;
import com.brijframework.client.device.model.UIDeviceMindSetItem;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;
import com.brijframework.client.entities.EOMindSetGroup;
import com.brijframework.client.entities.EOMindSetItem;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.forgin.model.ResourceFile;
import com.brijframework.client.forgin.repository.ResourceClient;
import com.brijframework.client.repository.MindSetGroupRepository;
import com.brijframework.client.repository.MindSetItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

@Service
public class DeviceMindSetServiceImpl extends CrudServiceImpl<UIDeviceMindSetGroup, EOMindSetGroup, Long> implements DeviceMindSetService {

	private static final String MINDSET_DATE = "mindsetDate";

	private static final String MINDSET = "mindset";

	@Autowired
	private MindSetGroupRepository clientMindSetGroupRepository;

	@Autowired
	private DeviceMindSetGroupMapper clientMindSetGroupMapper;
	
	@Autowired
	private MindSetItemRepository clientMindSetItemRepository;

	@Autowired
	private DeviceMindSetItemMapper clientMindSetItemMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	@Override
	public JpaRepository<EOMindSetGroup, Long> getRepository() {
		return clientMindSetGroupRepository;
	}

	@Override
	public GenericMapper<EOMindSetGroup, UIDeviceMindSetGroup> getMapper() {
		return clientMindSetGroupMapper;
	}
	
	{
		CustomPredicate<EOMindSetGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOMindSetGroup> mindsetDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> mindsetDatePath = root.get(MINDSET_DATE);
			In<Object> mindsetDateIn = criteriaBuilder.in(mindsetDatePath);
			DateFormat timeFormat = new SimpleDateFormat(DEVICE_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			mindsetDateIn.value(new java.sql.Date(date.getTime()) );
			return mindsetDateIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(MINDSET_DATE, mindsetDate);
	}

	@Override
	public List<String> ignoreProperties() {
		List<String> ignoreProperties = super.ignoreProperties();
		ignoreProperties.addAll(FieldUtil.getFieldList(EOEntityObject.class, ReflectionAccess.PRIVATE));
		ignoreProperties.add(CUST_BUSINESS_APP);
		return ignoreProperties;
	}

	@Override
	public void preAdd(UIDeviceMindSetGroup data, Map<String, List<String>> headers) {
		data.setRecordState(RecordStatus.ACTIVETED.getStatus());
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(MINDSET);
			resourceClient.add(MINDSET, resource.getFileName(), resource.getFileContent());
		}
		for(UIDeviceMindSetItem mindSetItem:   data.getMindSets()) {
			ResourceFile resourceFile = mindSetItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(MINDSET);
				resourceClient.add(MINDSET, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIDeviceMindSetGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(MINDSET);
			resourceClient.add(MINDSET, resource.getFileName(), resource.getFileContent());
		}
		for(UIDeviceMindSetItem mindSetItem:   data.getMindSets()) {
			ResourceFile resourceFile = mindSetItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(MINDSET);
				resourceClient.add(MINDSET, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preAdd(UIDeviceMindSetGroup data, EOMindSetGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	

	@Override
	public void preUpdate(UIDeviceMindSetGroup data, EOMindSetGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIDeviceMindSetGroup dtoObject, EOMindSetGroup entityObject,
			UIDeviceMindSetGroup updateDtoObject, EOMindSetGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOMindSetItem> mindSetItems = clientMindSetItemMapper.mapToDAO(dtoObject.getMindSets());
		mindSetItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOMindSetItem> mindSetItemsReturn = clientMindSetItemRepository.saveAll(mindSetItems);
		updateDtoObject.setMindSets(clientMindSetItemMapper.mapToDTO(mindSetItemsReturn));
	}

	@Override
	public List<EOMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
