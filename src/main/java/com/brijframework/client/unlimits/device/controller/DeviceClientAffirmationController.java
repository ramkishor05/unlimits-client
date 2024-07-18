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

import com.brijframework.client.unlimits.device.service.DeviceClientAffirmationService;
import com.brijframework.client.unlimits.entities.EOClientAffirmationGroup;
import com.brijframework.client.unlimits.model.UIClientAffirmationGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/affirmation"})
@CrossOrigin("*")
public class DeviceClientAffirmationController implements CrudController<UIClientAffirmationGroup, EOClientAffirmationGroup, Long>{
	
	@Autowired
	private DeviceClientAffirmationService clientAffirmationService;

	@Override
	public CrudService<UIClientAffirmationGroup, EOClientAffirmationGroup, Long> getService() {
		return clientAffirmationService;
	}

}
