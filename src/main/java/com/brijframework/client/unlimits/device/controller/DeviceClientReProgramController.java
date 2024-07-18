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

import com.brijframework.client.unlimits.device.service.DeviceClientReProgramService;
import com.brijframework.client.unlimits.entities.EOClientReProgramGroup;
import com.brijframework.client.unlimits.model.UIClientReProgramGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/reprogram"})
@CrossOrigin("*")
public class DeviceClientReProgramController implements CrudController<UIClientReProgramGroup, EOClientReProgramGroup, Long>{
	
	@Autowired
	private DeviceClientReProgramService clientReProgramService;

	@Override
	public CrudService<UIClientReProgramGroup, EOClientReProgramGroup, Long> getService() {
		return clientReProgramService;
	}

}
