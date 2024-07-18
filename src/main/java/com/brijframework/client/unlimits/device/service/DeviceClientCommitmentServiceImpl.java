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
import com.brijframework.client.mapper.ClientCommitmentGroupMapper;
import com.brijframework.client.repository.ClientCommitmentGroupRepository;
import com.brijframework.client.unlimits.entities.EOClientCommitmentGroup;
import com.brijframework.client.unlimits.model.UIClientCommitmentGroup;

/**
 * @author omnie
 */
@Service
public class DeviceClientCommitmentServiceImpl extends CrudServiceImpl<UIClientCommitmentGroup, EOClientCommitmentGroup, Long>
		implements DeviceClientCommitmentService {

	@Autowired
	private ClientCommitmentGroupRepository clientCommitmentRepository;

	@Autowired
	private ClientCommitmentGroupMapper clientCommitmentMapper;

	@Override
	public JpaRepository<EOClientCommitmentGroup, Long> getRepository() {
		return clientCommitmentRepository;
	}

	@Override
	public GenericMapper<EOClientCommitmentGroup, UIClientCommitmentGroup> getMapper() {
		return clientCommitmentMapper;
	}

	@Override
	public void preAdd(UIClientCommitmentGroup data, EOClientCommitmentGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.getCommitments().forEach(item->item.setGroup(entity));
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientCommitmentGroup data, EOClientCommitmentGroup entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.getCommitments().forEach(item->item.setGroup(entity));
		entity.setCustBusinessApp(eoCustBusinessApp);
	}


	@Override
	public List<EOClientCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientCommitmentRepository.findAllByCustBusinessApp(eoCustBusinessApp, Sort.by("CommitmentDate").descending());
	}

	@Override
	public Page<EOClientCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientCommitmentRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	public List<EOClientCommitmentGroup> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, String> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientCommitmentRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
