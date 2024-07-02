/**
 * 
 */
package com.brijframework.client.unlimits.service;

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
import com.brijframework.client.mapper.ClientJournalMapper;
import com.brijframework.client.repository.ClientJournalRepository;
import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournal;

/**
 * @author omnie
 */
@Service
public class ClientJournalServiceImpl extends CrudServiceImpl<UIClientJournal, EOClientJournal, Long>
		implements ClientJournalService {

	@Autowired
	private ClientJournalRepository clientJournalRepository;

	@Autowired
	private ClientJournalMapper clientJournalMapper;

	@Override
	public JpaRepository<EOClientJournal, Long> getRepository() {
		return clientJournalRepository;
	}

	@Override
	public GenericMapper<EOClientJournal, UIClientJournal> getMapper() {
		return clientJournalMapper;
	}

	@Override
	protected void preAdd(UIClientJournal data, EOClientJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	protected void preUpdate(UIClientJournal data, EOClientJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}


	@Override
	protected List<EOClientJournal> repositoryFindAll(Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientJournalRepository.findAllByCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	protected Page<EOClientJournal> repositoryFindAll(Map<String, List<String>> headers, Pageable pageable) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientJournalRepository.findAllByCustBusinessApp(eoCustBusinessApp, pageable);
	}

	@Override
	protected List<EOClientJournal> repositoryFindAll(Map<String, List<String>> headers, Sort sort) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		return clientJournalRepository.findAllByCustBusinessApp(eoCustBusinessApp, sort);
	}

}
