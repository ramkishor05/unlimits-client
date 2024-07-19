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
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.forgin.model.ResourceFile;
import com.brijframework.client.forgin.repository.ResourceClient;
import com.brijframework.client.mapper.ClientMindSetGroupMapper;
import com.brijframework.client.mapper.ClientMindSetItemMapper;
import com.brijframework.client.repository.ClientMindSetGroupRepository;
import com.brijframework.client.repository.ClientMindSetItemRepository;
import com.brijframework.client.unlimits.entities.EOClientMindSetGroup;
import com.brijframework.client.unlimits.entities.EOClientMindSetItem;
import com.brijframework.client.unlimits.model.UIClientMindSetGroup;
import com.brijframework.client.unlimits.model.UIClientMindSetItem;

@Service
public class DeviceClientMindSetServiceImpl extends CrudServiceImpl<UIClientMindSetGroup, EOClientMindSetGroup, Long> implements DeviceClientMindSetService {

	private static final String MINDSET = "mindset";

	@Autowired
	private ClientMindSetGroupRepository clientMindSetGroupRepository;

	@Autowired
	private ClientMindSetGroupMapper clientMindSetGroupMapper;
	
	@Autowired
	private ClientMindSetItemRepository clientMindSetItemRepository;

	@Autowired
	private ClientMindSetItemMapper clientMindSetItemMapper;
	
	@Autowired
	private ResourceClient resourceRepository;
	
	@Override
	public JpaRepository<EOClientMindSetGroup, Long> getRepository() {
		return clientMindSetGroupRepository;
	}

	@Override
	public GenericMapper<EOClientMindSetGroup, UIClientMindSetGroup> getMapper() {
		return clientMindSetGroupMapper;
	}
	
	@Override
	public void preAdd(UIClientMindSetGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(MINDSET);
			resourceRepository.add(MINDSET, resource.getFileName(), resource.getFileContent());
		}
		for(UIClientMindSetItem mindSetItem:   data.getMindSets()) {
			ResourceFile resourceFile = mindSetItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(MINDSET);
				resourceRepository.add(MINDSET, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIClientMindSetGroup data, Map<String, List<String>> headers) {
		ResourceFile resource = data.getContent();
		if(resource!=null) {
			resource.setFolderName(MINDSET);
			resourceRepository.add(MINDSET, resource.getFileName(), resource.getFileContent());
		}
		for(UIClientMindSetItem mindSetItem:   data.getMindSets()) {
			ResourceFile resourceFile = mindSetItem.getContent();
			if(resourceFile!=null) {
				resourceFile.setFolderName(MINDSET);
				resourceRepository.add(MINDSET, resourceFile.getFileName(), resourceFile.getFileContent());
			}
		}
	}
	
	@Override
	public void preAdd(UIClientMindSetGroup data, EOClientMindSetGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}
	

	@Override
	public void preUpdate(UIClientMindSetGroup data, EOClientMindSetGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIClientMindSetGroup dtoObject, EOClientMindSetGroup entityObject,
			UIClientMindSetGroup updateDtoObject, EOClientMindSetGroup updateEntityObject,
			Map<String, List<String>> headers) {
		List<EOClientMindSetItem> mindSetItems = clientMindSetItemMapper.mapToDAO(dtoObject.getMindSets());
		mindSetItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOClientMindSetItem> mindSetItemsReturn = clientMindSetItemRepository.saveAll(mindSetItems);
		updateDtoObject.setMindSets(clientMindSetItemMapper.mapToDTO(mindSetItemsReturn));
	}

	@Override
	public List<EOClientMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientMindSetGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
