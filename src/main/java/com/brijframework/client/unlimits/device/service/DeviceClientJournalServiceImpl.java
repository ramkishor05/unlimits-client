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
import org.springframework.util.CollectionUtils;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientJournalMapper;
import com.brijframework.client.repository.ClientJournalRepository;
import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournalItem;

/**
 * @author omnie
 */
@Service
public class DeviceClientJournalServiceImpl extends CrudServiceImpl<UIClientJournalItem, EOClientJournal, Long>
		implements DeviceClientJournalService {

	@Autowired
	private ClientJournalRepository clientJournalRepository;

	@Autowired
	private ClientJournalMapper clientJournalMapper;

	@Override
	public JpaRepository<EOClientJournal, Long> getRepository() {
		return clientJournalRepository;
	}

	@Override
	public GenericMapper<EOClientJournal, UIClientJournalItem> getMapper() {
		return clientJournalMapper;
	}

	@Override
	public void preAdd(UIClientJournalItem data, EOClientJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		List<EOClientJournal> findClientJournalList = clientJournalRepository.findAllByCustBusinessAppAndJournalId(eoCustBusinessApp, entity.getJournalId());
		if(!CollectionUtils.isEmpty(findClientJournalList)) {
			entity.setId(findClientJournalList.get(0).getId());
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIClientJournalItem data, EOClientJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		List<EOClientJournal> findClientJournalList = clientJournalRepository.findAllByCustBusinessAppAndJournalId(eoCustBusinessApp, entity.getJournalId());
		if(!CollectionUtils.isEmpty(findClientJournalList)) {
			entity.setId(findClientJournalList.get(0).getId());
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public List<EOClientJournal> repositoryFindAll(Map<String, List<String>> headers, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers, filters);
	}

	@Override
	public Page<EOClientJournal> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers,pageable, filters);
	}

	@Override
	public List<EOClientJournal> repositoryFindAll(Map<String, List<String>> headers, Sort sort, Map<String, Object> filters) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		filters.put("custBusinessApp", eoCustBusinessApp);
		return super.repositoryFindAll(headers, sort, filters);
	}

}
