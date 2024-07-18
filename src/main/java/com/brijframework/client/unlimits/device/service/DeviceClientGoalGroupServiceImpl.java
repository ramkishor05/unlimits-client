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
import com.brijframework.client.mapper.ClientGoalGroupMapper;
import com.brijframework.client.mapper.ClientGoalItemMapper;
import com.brijframework.client.repository.ClientGoalGroupRepository;
import com.brijframework.client.repository.ClientGoalItemRepository;
import com.brijframework.client.unlimits.entities.EOClientGoalGroup;
import com.brijframework.client.unlimits.entities.EOClientGoalItem;
import com.brijframework.client.unlimits.model.UIClientGoalGroup;

/**
 * @author omnie
 */
@Service
public class DeviceClientGoalGroupServiceImpl extends CrudServiceImpl<UIClientGoalGroup, EOClientGoalGroup, Long>
		implements DeviceClientGoalGroupService {

	@Autowired
	private ClientGoalGroupRepository clientGoalGroupRepository;

	@Autowired
	private ClientGoalGroupMapper clientGoalGroupMapper;
	
	@Autowired
	private ClientGoalItemRepository clientGoalItemRepository;

	@Autowired
	private ClientGoalItemMapper clientGoalItemMapper;

	@Override
	public JpaRepository<EOClientGoalGroup, Long> getRepository() {
		return clientGoalGroupRepository;
	}

	@Override
	public GenericMapper<EOClientGoalGroup, UIClientGoalGroup> getMapper() {
		return clientGoalGroupMapper;
	}

	@Override
	public void preAdd(UIClientGoalGroup data, EOClientGoalGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientGoalGroup data, EOClientGoalGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void merge(UIClientGoalGroup dtoObject, EOClientGoalGroup entityObject, UIClientGoalGroup updateDtoObject,
			EOClientGoalGroup updateEntityObject, Map<String, List<String>> headers) {
		List<EOClientGoalItem> goalItems = clientGoalItemMapper.mapToDAO(dtoObject.getGoals());
		goalItems.forEach(item->item.setGroup(updateEntityObject));
		List<EOClientGoalItem> goalItemsReturn = clientGoalItemRepository.saveAll(goalItems);
		updateDtoObject.setGoals(clientGoalItemMapper.mapToDTO(goalItemsReturn));
	}

	@Override
	public List<EOClientGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientGoalGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("goalDate").descending());
	}

	@Override
	public Page<EOClientGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientGoalGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOClientGoalGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientGoalGroupRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
