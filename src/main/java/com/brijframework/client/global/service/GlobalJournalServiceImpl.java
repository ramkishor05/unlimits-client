/**
 * 
 */
package com.brijframework.client.global.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOJournal;
import com.brijframework.client.exceptions.UserNotFoundException;
import com.brijframework.client.global.mapper.GlobalJournalMapper;
import com.brijframework.client.global.model.UIGlobalJournalItem;
import com.brijframework.client.repository.JournalRepository;

/**
 * @author omnie
 */
@Service
public class GlobalJournalServiceImpl extends CrudServiceImpl<UIGlobalJournalItem, EOJournal, Long>
		implements GlobalJournalService {

	@Autowired
	private JournalRepository journalRepository;

	@Autowired
	private GlobalJournalMapper globalJournalMapper;

	@Override
	public JpaRepository<EOJournal, Long> getRepository() {
		return journalRepository;
	}

	@Override
	public GenericMapper<EOJournal, UIGlobalJournalItem> getMapper() {
		return globalJournalMapper;
	}

	@Override
	public void preAdd(UIGlobalJournalItem data, EOJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

	@Override
	public void preUpdate(UIGlobalJournalItem data, EOJournal entity,
			Map<String, List<String>> headers) {
		EOCustBusinessApp eoCustBusinessApp = (EOCustBusinessApp) ApiSecurityContext.getContext().getCurrentAccount();
		if (eoCustBusinessApp == null) {
			throw new UserNotFoundException("Invalid client");
		}
		entity.setCustBusinessApp(eoCustBusinessApp);
	}

}
