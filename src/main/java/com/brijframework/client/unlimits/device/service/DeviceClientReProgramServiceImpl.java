/**
 * 
 */
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
import com.brijframework.client.forgin.model.UIResource;
import com.brijframework.client.forgin.repository.ResourceRepository;
import com.brijframework.client.mapper.ClientReProgramGroupMapper;
import com.brijframework.client.repository.ClientReProgramGroupRepository;
import com.brijframework.client.unlimits.entities.EOClientReProgramGroup;
import com.brijframework.client.unlimits.model.UIClientReProgramGroup;
import com.brijframework.client.unlimits.model.UIClientReProgramItem;

/**
 * @author omnie
 */
@Service
public class DeviceClientReProgramServiceImpl extends CrudServiceImpl<UIClientReProgramGroup, EOClientReProgramGroup, Long>
		implements DeviceClientReProgramService {

	private static final String REPROGRAM = "reprogram";

	@Autowired
	private ClientReProgramGroupRepository clientReProgramGroupRepository;

	@Autowired
	private ClientReProgramGroupMapper clientReProgramGroupMapper;

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public JpaRepository<EOClientReProgramGroup, Long> getRepository() {
		return clientReProgramGroupRepository;
	}

	@Override
	public GenericMapper<EOClientReProgramGroup, UIClientReProgramGroup> getMapper() {
		return clientReProgramGroupMapper;
	}
	
	@Override
	public void preAdd(UIClientReProgramGroup data, Map<String, List<String>> headers) {
		for(UIClientReProgramItem reProgramItem:   data.getReprograms()) {
			UIResource resource = reProgramItem.getResource();
			if(resource!=null) {
				resource.setFolderName(REPROGRAM);
				resourceRepository.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
			}
		}
	}
	
	@Override
	public void preUpdate(UIClientReProgramGroup data, Map<String, List<String>> headers) {
		for(UIClientReProgramItem reProgramItem:   data.getReprograms()) {
			UIResource resource = reProgramItem.getResource();
			if(resource!=null) {
				resource.setFolderName(REPROGRAM);
				resourceRepository.add(REPROGRAM, resource.getFileName(), resource.getFileContent());
			}
		}
	}

	@Override
	public void preAdd(UIClientReProgramGroup data, EOClientReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.getReprograms().forEach(item->item.setGroup(entity));

		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientReProgramGroup data, EOClientReProgramGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.getReprograms().forEach(item->item.setGroup(entity));

		entity.setCustBusinessApp(eoCustBusinessApp);
	}


	@Override
	public List<EOClientReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientReProgramGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("name").descending());
	}

	@Override
	public Page<EOClientReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientReProgramGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOClientReProgramGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientReProgramGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}