/**
 * 
 */
package com.brijframework.client.unlimits.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.device.service.DeviceClientJournalService;
import com.brijframework.client.unlimits.entities.EOClientJournal;
import com.brijframework.client.unlimits.model.UIClientJournal;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/journals", "/api/client/journals"})
@CrossOrigin("*")
public class DeviceClientJournalController extends CrudController<UIClientJournal, EOClientJournal, Long>{
	
	@Autowired
	private DeviceClientJournalService clientJournalService;

	@Override
	public CrudService<UIClientJournal, EOClientJournal, Long> getService() {
		return clientJournalService;
	}
}
