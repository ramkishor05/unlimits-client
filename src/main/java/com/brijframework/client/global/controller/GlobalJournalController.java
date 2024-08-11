/**
 * 
 */
package com.brijframework.client.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.EOJournal;
import com.brijframework.client.global.model.UIGlobalJournalItem;
import com.brijframework.client.global.service.GlobalJournalService;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/journals")
@CrossOrigin("*")
@Hidden
public class GlobalJournalController implements CrudController<UIGlobalJournalItem, EOJournal, Long>{
	
	@Autowired
	private GlobalJournalService globalJournalService;

	@Override
	public CrudService<UIGlobalJournalItem, EOJournal, Long> getService() {
		return globalJournalService;
	}
}
