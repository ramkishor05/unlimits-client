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

import com.brijframework.client.device.model.UIDeviceMindSetGroup;
import com.brijframework.client.device.service.DeviceMindSetService;
import com.brijframework.client.entities.EOMindSetGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/mindset"})
@CrossOrigin("*")
public class DeviceMindSetController implements CrudController<UIDeviceMindSetGroup, EOMindSetGroup, Long>{
	
	@Autowired
	private DeviceMindSetService clientMindSetService;

	@Override
	public CrudService<UIDeviceMindSetGroup, EOMindSetGroup, Long> getService() {
		return clientMindSetService;
	}

}
