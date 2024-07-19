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
import com.brijframework.client.forgin.model.ResourceFile;
import com.brijframework.client.forgin.repository.ResourceClient;
import com.brijframework.client.mapper.ClientAffirmationGroupMapper;
import com.brijframework.client.mapper.ClientAffirmationItemMapper;
import com.brijframework.client.repository.ClientAffirmationGroupRepository;
import com.brijframework.client.repository.ClientAffirmationItemRepository;
import com.brijframework.client.unlimits.entities.EOClientAffirmationGroup;
import com.brijframework.client.unlimits.entities.EOClientAffirmationItem;
import com.brijframework.client.unlimits.model.UIClientAffirmationGroup;
import com.brijframework.client.unlimits.model.UIClientAffirmationItem;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;
@Service
public class DeviceClientAffirmationServiceImpl extends CrudServiceImpl<UIClientAffirmationGroup, EOClientAffirmationGroup, Long>  implements DeviceClientAffirmationService {

	private static final String AFFIRMATION = "affirmation";

	@Autowired
	private ClientAffirmationGroupRepository clientAffirmationGroupRepository;
	
	@Autowired
	private ClientAffirmationItemRepository clientAffirmationItemRepository;

	@Autowired
	private ClientAffirmationGroupMapper clientAffirmationGroupMapper;
	
	@Autowired
	private ClientAffirmationItemMapper clientAffirmationItemMapper;
	
	@Autowired
	private ResourceClient resourceRepository;
	
	{
		CustomPredicate<EOClientAffirmationGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOClientAffirmationGroup> affirmationDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> custBusinessAppPath = root.get("affirmationDate");
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
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
	public JpaRepository<EOClientAffirmationGroup, Long> getRepository() {
		return clientAffirmationGroupRepository;
	}

	@Override
	public GenericMapper<EOClientAffirmationGroup, UIClientAffirmationGroup> getMapper() {
		return clientAffirmationGroupMapper;
	}
	
	@Override
	public void preAdd(UIClientAffirmationGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(AFFIRMATION);
			resourceRepository.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
		}
		for(UIClientAffirmationItem AffirmationItem:   data.getAffirmations()) {
			ResourceFile resourceFile = AffirmationItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(AFFIRMATION);
				resourceRepository.add(AFFIRMATION, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIClientAffirmationGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(AFFIRMATION);
			resourceRepository.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
		}
		for(UIClientAffirmationItem AffirmationItem:   data.getAffirmations()) {
			ResourceFile resourceFile = AffirmationItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(AFFIRMATION);
				resourceRepository.add(AFFIRMATION, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIClientAffirmationGroup data, EOClientAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIClientAffirmationGroup data, EOClientAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	
	@Override
	public void merge(UIClientAffirmationGroup dtoObject, EOClientAffirmationGroup entityObject,
			UIClientAffirmationGroup updateDtoObject, EOClientAffirmationGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientAffirmationItem> affirmationItems = clientAffirmationItemMapper.mapToDAO(dtoObject.getAffirmations());
		affirmationItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOClientAffirmationItem> affirmationItemsReturn = clientAffirmationItemRepository.saveAll(affirmationItems);
		updateDtoObject.setAffirmations(clientAffirmationItemMapper.mapToDTO(affirmationItemsReturn));
	}


	@Override
	public List<EOClientAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
