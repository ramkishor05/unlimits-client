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

import com.brijframework.client.unlimits.entities.EOCustJournal;
import com.brijframework.client.unlimits.global.service.GlobalCustJournalService;
import com.brijframework.client.unlimits.model.UICustJournalItem;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/journals")
@CrossOrigin("*")
@Hidden
public class GlobalCustJournalController implements CrudController<UICustJournalItem, EOCustJournal, Long>{
	
	@Autowired
	private GlobalCustJournalService globalCustJournalService;

	@Override
	public CrudService<UICustJournalItem, EOCustJournal, Long> getService() {
		return globalCustJournalService;
	}
}
