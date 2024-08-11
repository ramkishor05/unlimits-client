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

import com.brijframework.client.device.mapper.DeviceReProgramGroupMapper;
import com.brijframework.client.device.mapper.DeviceReProgramItemMapper;
import com.brijframework.client.device.model.UIDeviceReProgramGroup;
import com.brijframework.client.device.model.UIDeviceReProgramItem;
import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOReProgramGroup;
import com.brijframework.client.entities.EOReProgramItem;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.forgin.model.ResourceFile;
import com.brijframework.client.forgin.repository.ResourceClient;
import com.brijframework.client.repository.ReProgramGroupRepository;
import com.brijframework.client.repository.ReProgramItemRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Path;

/**
 * @author omnie
 */
@Service
public class DeviceReProgramServiceImpl extends CrudServiceImpl<UIDeviceReProgramGroup, EOReProgramGroup, Long>
		implements DeviceReProgramService {

	private static final String REPROGRAM_DATE = "reprogramDate";

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private ReProgramGroupRepository clientReProgramGroupRepository;

	@Autowired
	private DeviceReProgramGroupMapper clientReProgramGroupMapper;
	
	@Autowired
	private ReProgramItemRepository clientReProgramItemRepository;

	@Autowired
	private DeviceReProgramItemMapper clientReProgramItemMapper;

	@Autowired
	private ResourceClient resourceClient;

	@Override
	public JpaRepository<EOReProgramGroup, Long> getRepository() {
		return clientReProgramGroupRepository;
	}

	@Override
	public GenericMapper<EOReProgramGroup, UIDeviceReProgramGroup> getMapper() {
		return clientReProgramGroupMapper;
	}
	
	{
		CustomPredicate<EOReProgramGroup> custBusinessApp = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Object> custBusinessAppPath = root.get(CUST_BUSINESS_APP);
			In<Object> custBusinessAppIn = criteriaBuilder.in(custBusinessAppPath);
			custBusinessAppIn.value(filter.getColumnValue());
			return custBusinessAppIn;
		};
		
		CustomPredicate<EOReProgramGroup> reprogramDate = (type, root, criteriaQuery, criteriaBuilder, filter) -> {
			Path<Date> reprogramPath = root.get(REPROGRAM_DATE);
			In<Object> reprogramIn = criteriaBuilder.in(reprogramPath);
			DateFormat timeFormat = new SimpleDateFormat(DEVICE_DATE_FORMAT_MMMM_DD_YYYY);
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
	public void preAdd(UIDeviceReProgramGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(REPROGRAM);
			resourceClient.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
		}
		for(UIDeviceReProgramItem reProgramItem:   data.getReprograms()) {
			ResourceFile resourceFile = reProgramItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(REPROGRAM);
				resourceClient.add(REPROGRAM, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIDeviceReProgramGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(REPROGRAM);
			resourceClient.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
		}
		for(UIDeviceReProgramItem reProgramItem:   data.getReprograms()) {
			ResourceFile resourceFile = reProgramItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(REPROGRAM);
				resourceClient.add(REPROGRAM, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIDeviceReProgramGroup data, EOReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIDeviceReProgramGroup data, EOReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void merge(UIDeviceReProgramGroup dtoObject, EOReProgramGroup entityObject,
			UIDeviceReProgramGroup updateDtoObject, EOReProgramGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOReProgramItem> reProgramItems = clientReProgramItemMapper.mapToDAO(dtoObject.getReprograms());
		reProgramItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOReProgramItem> reProgramItemsReturn = clientReProgramItemRepository.saveAll(reProgramItems);
		updateDtoObject.setReprograms(clientReProgramItemMapper.mapToDTO(reProgramItemsReturn));
	}


	@Override
	public List<EOReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException(INVALID_CLIENT);
		}
		filters.put(CUST_BUSINESS_APP, eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}
}