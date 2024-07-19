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
import com.brijframework.client.forgin.model.ResourceFile;
import com.brijframework.client.forgin.repository.ResourceClient;
import com.brijframework.client.mapper.ClientReProgramGroupMapper;
import com.brijframework.client.mapper.ClientReProgramItemMapper;
import com.brijframework.client.repository.ClientReProgramGroupRepository;
import com.brijframework.client.repository.ClientReProgramItemRepository;
import com.brijframework.client.unlimits.entities.EOClientReProgramGroup;
import com.brijframework.client.unlimits.entities.EOClientReProgramItem;
import com.brijframework.client.unlimits.model.UIClientReProgramGroup;
import com.brijframework.client.unlimits.model.UIClientReProgramItem;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceClientReProgramServiceImpl extends CrudServiceImpl<UIClientReProgramGroup, EOClientReProgramGroup, Long>
		implements DeviceClientReProgramService {

	private static final String REPROGRAM_DATE = "reprogramDate";

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private ClientReProgramGroupRepository clientReProgramGroupRepository;

	@Autowired
	private ClientReProgramGroupMapper clientReProgramGroupMapper;
	
	@Autowired
	private ClientReProgramItemRepository clientReProgramItemRepository;

	@Autowired
	private ClientReProgramItemMapper clientReProgramItemMapper;

	@Autowired
	private ResourceClient resourceRepository;

	@Override
	public JpaRepository<EOClientReProgramGroup, Long> getRepository() {
		return clientReProgramGroupRepository;
	}

	@Override
	public GenericMapper<EOClientReProgramGroup, UIClientReProgramGroup> getMapper() {
		return clientReProgramGroupMapper;
	}
	
	{
		CustomPredicate<EOClientReProgramGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOClientReProgramGroup> reprogramDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> reprogramPath = root.get(REPROGRAM_DATE);
			In<Object> reprogramIn = criteriaBuilder.in(reprogramPath);
			DateFormat timeFormat = new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY);
			Date date = null;
			try {
				date = timeFormat.parse(filter.getColumnValue().toString());
			} catch (ParseException e) {
				System.err.println("WARN: unexpected object in Object.dateValue(): " + filter.getColumnValue());
			}
			reprogramIn.value(new java.sql.Date(date.getTime()) );
			return reprogramIn;
		};
 
		addCustomPredicate(CUST_BUSINESS_APP, custBusinessApp);
		addCustomPredicate(REPROGRAM_DATE, reprogramDate);
	}
	
	@Override
	public void preAdd(UIClientReProgramGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(REPROGRAM);
			resourceRepository.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
		}
		for(UIClientReProgramItem reProgramItem:   data.getReprograms()) {
			ResourceFile resourceFile = reProgramItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(REPROGRAM);
				resourceRepository.add(REPROGRAM, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIClientReProgramGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(REPROGRAM);
			resourceRepository.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
		}
		for(UIClientReProgramItem reProgramItem:   data.getReprograms()) {
			ResourceFile resourceFile = reProgramItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(REPROGRAM);
				resourceRepository.add(REPROGRAM, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIClientReProgramGroup data, EOClientReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIClientReProgramGroup data, EOClientReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UIClientReProgramGroup dtoObject, EOClientReProgramGroup entityObject,
			UIClientReProgramGroup updateDtoObject, EOClientReProgramGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientReProgramItem> reProgramItems = clientReProgramItemMapper.mapToDAO(dtoObject.getReprograms());
		reProgramItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOClientReProgramItem> reProgramItemsReturn = clientReProgramItemRepository.saveAll(reProgramItems);
		updateDtoObject.setReprograms(clientReProgramItemMapper.mapToDTO(reProgramItemsReturn));
	}


	@Override
	public List<EOClientReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}
