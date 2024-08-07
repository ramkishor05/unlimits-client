/**
 * 
 */
package com.brijframework.client.unlimits.global.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.mapper.ClientJournalMapper;
import com.brijframework.client.repository.ClientJournalRepository;
import com.brijframework.client.unlimits.entities.EOCustJournal;
import com.brijframework.client.unlimits.model.UICustJournalItem;

/**
 * @author omnie
 */
@Service
public class GlobalClientJournalServiceImpl extends CrudServiceImpl<UICustJournalItem, EOCustJournal, Long>
		implements GlobalCustJournalService {

	@Autowired
	private ClientJournalRepository clientJournalRepository;

	@Autowired
	private ClientJournalMapper clientJournalMapper;

	@Override
	public JpaRepository<EOCustJournal, Long> getRepository() {
		return clientJournalRepository;
	}

	@Override
	public GenericMapper<EOCustJournal, UICustJournalItem> getMapper() {
		return clientJournalMapper;
	}

	@Override
	public void preAdd(UICustJournalItem data, EOCustJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UICustJournalItem data, EOCustJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

}
