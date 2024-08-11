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

import com.brijframework.client.device.model.UIDeviceReProgramGroup;
import com.brijframework.client.device.service.DeviceReProgramService;
import com.brijframework.client.entities.EOReProgramGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/reprogram"})
@CrossOrigin("*")
public class DeviceReProgramController implements CrudController<UIDeviceReProgramGroup, EOReProgramGroup, Long>{
	
	@Autowired
	private DeviceReProgramService clientReProgramService;

	@Override
	public CrudService<UIDeviceReProgramGroup, EOReProgramGroup, Long> getService() {
		return clientReProgramService;
	}

}
