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

import com.brijframework.client.device.mapper.DeviceAffirmationGroupMapper;
import com.brijframework.client.device.mapper.DeviceAffirmationItemMapper;
import com.brijframework.client.device.model.UIDeviceAffirmationGroup;
import com.brijframework.client.device.model.UIDeviceAffirmationItem;
import com.brijframework.client.entities.EOAffirmationGroup;
import com.brijframework.client.entities.EOAffirmationItem;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.forgin.model.ResourceFile;
import com.brijframework.client.forgin.repository.ResourceClient;
import com.brijframework.client.repository.AffirmationGroupRepository;
import com.brijframework.client.repository.AffirmationItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
@Service
public class DeviceAffirmationServiceImpl extends CrudServiceImpl<UIDeviceAffirmationGroup, EOAffirmationGroup, Long>  implements DeviceAffirmationService {

	private static final String AFFIRMATION = "affirmation";

	@Autowired
	private AffirmationGroupRepository clientAffirmationGroupRepository;
	
	@Autowired
	private AffirmationItemRepository clientAffirmationItemRepository;

	@Autowired
	private DeviceAffirmationGroupMapper clientAffirmationGroupMapper;
	
	@Autowired
	private DeviceAffirmationItemMapper clientAffirmationItemMapper;
	
	@Autowired
	private ResourceClient resourceClient;
	
	{
		CustomPredicate<EOAffirmationGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOAffirmationGroup> affirmationDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> custBusinessAppPath = root.get("affirmationDate");
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			DateFormat timeFormat = new SimpleDateFormat(DEVICE_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			custBusinessAppIn.value(new java.sql.Date(date.getTime()) );
			return custBusinessAppIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate("affirmationDate", affirmationDate);
	}
	
	@Override
	public JpaRepository<EOAffirmationGroup, Long> getRepository() {
		return clientAffirmationGroupRepository;
	}

	@Override
	public GenericMapper<EOAffirmationGroup, UIDeviceAffirmationGroup> getMapper() {
		return clientAffirmationGroupMapper;
	}
	
	@Override
	public void preAdd(UIDeviceAffirmationGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(AFFIRMATION);
			resourceClient.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
		}
		for(UIDeviceAffirmationItem AffirmationItem:   data.getAffirmations()) {
			ResourceFile resourceFile = AffirmationItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(AFFIRMATION);
				resourceClient.add(AFFIRMATION, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIDeviceAffirmationGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(AFFIRMATION);
			resourceClient.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
		}
		for(UIDeviceAffirmationItem AffirmationItem:   data.getAffirmations()) {
			ResourceFile resourceFile = AffirmationItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(AFFIRMATION);
				resourceClient.add(AFFIRMATION, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIDeviceAffirmationGroup data, EOAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIDeviceAffirmationGroup data, EOAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	
	@Override
	public void merge(UIDeviceAffirmationGroup dtoObject, EOAffirmationGroup entityObject,
			UIDeviceAffirmationGroup updateDtoObject, EOAffirmationGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOAffirmationItem> affirmationItems = clientAffirmationItemMapper.mapToDAO(dtoObject.getAffirmations());
		affirmationItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOAffirmationItem> affirmationItemsReturn = clientAffirmationItemRepository.saveAll(affirmationItems);
		updateDtoObject.setAffirmations(clientAffirmationItemMapper.mapToDTO(affirmationItemsReturn));
	}


	@Override
	public List<EOAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
