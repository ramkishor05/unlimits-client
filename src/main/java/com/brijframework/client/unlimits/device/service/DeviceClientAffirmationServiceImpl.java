package com.brijframework.client.unlimits.device.service;

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

@Service
public class DeviceClientAffirmationServiceImpl implements DeviceClientAffirmationService {

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
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	
	@Override
	public void preUpdate(UIClientAffirmationGroup data, EOClientAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
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
	public List<EOClientAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientAffirmationGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("name").descending());
	}

	@Override
	public Page<EOClientAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientAffirmationGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOClientAffirmationGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientAffirmationGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
