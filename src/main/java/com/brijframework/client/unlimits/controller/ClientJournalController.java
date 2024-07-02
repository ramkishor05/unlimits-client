/**
 * 
 */
package com.brijframework.client.unlimits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournal;
import com.brijframework.client.unlimits.service.ClientJournalService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/client/journals")
@CrossOrigin("*")
public class ClientJournalController extends CrudController<UIClientJournal, EOClientJournal, Long>{
	
	@Autowired
	private ClientJournalService clientJournalService;

	@Override
	public CrudService<UIClientJournal, EOClientJournal, Long> getService() {
		return clientJournalService;
	}
}
