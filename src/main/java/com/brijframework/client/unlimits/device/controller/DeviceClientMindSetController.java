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

import com.brijframework.client.unlimits.device.service.DeviceClientMindSetService;
import com.brijframework.client.unlimits.entities.EOClientMindSetGroup;
import com.brijframework.client.unlimits.model.UIClientMindSetGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/mindset"})
@CrossOrigin("*")
public class DeviceClientMindSetController implements CrudController<UIClientMindSetGroup, EOClientMindSetGroup, Long>{
	
	@Autowired
	private DeviceClientMindSetService clientMindSetService;

	@Override
	public CrudService<UIClientMindSetGroup, EOClientMindSetGroup, Long> getService() {
		return clientMindSetService;
	}

}
