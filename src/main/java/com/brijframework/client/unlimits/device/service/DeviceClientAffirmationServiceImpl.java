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
import com.brijframework.client.forgin.model.UIResource;
import com.brijframework.client.forgin.repository.ResourceRepository;
import com.brijframework.client.mapper.ClientAffirmationGroupMapper;
import com.brijframework.client.repository.ClientAffirmationGroupRepository;
import com.brijframework.client.unlimits.entities.EOClientAffirmationGroup;
import com.brijframework.client.unlimits.model.UIClientAffirmationGroup;
import com.brijframework.client.unlimits.model.UIClientAffirmationItem;

@Service
public class DeviceClientAffirmationServiceImpl implements DeviceClientAffirmationService {

	private static final String AFFIRMATION = "affirmation";

	@Autowired
	private ClientAffirmationGroupRepository clientAffirmationGroupRepository;

	@Autowired
	private ClientAffirmationGroupMapper clientAffirmationGroupMapper;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
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
		for(UIClientAffirmationItem AffirmationItem:   data.getAffirmations()) {
			UIResource resource = AffirmationItem.getResource();
			if(resource!=null) {
				resource.setFolderName(AFFIRMATION);
				resourceRepository.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIClientAffirmationGroup data, Map<String, List<String>> headers) {
		for(UIClientAffirmationItem AffirmationItem:   data.getAffirmations()) {
			UIResource resource = AffirmationItem.getResource();
			if(resource!=null) {
				resource.setFolderName(AFFIRMATION);
				resourceRepository.add(AFFIRMATION, resource.getFileName(), resource.getFileContent());
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
		entity.getAffirmations().forEach(item->item.setGroup(entity));
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientAffirmationGroup data, EOClientAffirmationGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.getAffirmations().forEach(item->item.setGroup(entity));
		entity.setCustBusinessApp(eoCustBusinessApp);
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
