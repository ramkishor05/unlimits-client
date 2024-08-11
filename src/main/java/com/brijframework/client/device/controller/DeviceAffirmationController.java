/**
 * 
 */
package com.brijframework.client.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceAffirmationGroup;
import com.brijframework.client.device.service.DeviceAffirmationService;
import com.brijframework.client.entities.EOAffirmationGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/affirmation"})
@CrossOrigin("*")
public class DeviceAffirmationController implements CrudController<UIDeviceAffirmationGroup, EOAffirmationGroup, Long>{
	
	@Autowired
	private DeviceAffirmationService clientAffirmationService;

	@Override
	public CrudService<UIDeviceAffirmationGroup, EOAffirmationGroup, Long> getService() {
		return clientAffirmationService;
	}

}
