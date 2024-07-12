/**
 * 
 */
package com.brijframework.client.unlimits.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.global.service.GlobalClientJournalService;
import com.brijframework.client.unlimits.model.UIClientJournal;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/client/journals")
@CrossOrigin("*")
public class GlobalClientJournalController implements CrudController<UIClientJournal, EOClientJournal, Long>{
	
	@Autowired
	private GlobalClientJournalService clientJournalService;

	@Override
	public CrudService<UIClientJournal, EOClientJournal, Long> getService() {
		return clientJournalService;
	}
}
