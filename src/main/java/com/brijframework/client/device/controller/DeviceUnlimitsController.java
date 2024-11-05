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

import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.service.DeviceUnlimitsService;
import com.brijframework.client.entities.EOUnlimits;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/unlimits"})
@CrossOrigin("*")
public class DeviceUnlimitsController implements CrudController<UIDeviceUnlimits, EOUnlimits, Long>{
	
	@Autowired
	private DeviceUnlimitsService clientUnlimitsService;

	@Override
	public CrudService<UIDeviceUnlimits, EOUnlimits, Long> getService() {
		return clientUnlimitsService;
	}
}
