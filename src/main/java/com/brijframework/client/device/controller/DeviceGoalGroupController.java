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

import com.brijframework.client.device.model.UIDeviceGoalGroup;
import com.brijframework.client.device.service.DeviceGoalGroupService;
import com.brijframework.client.entities.EOGoalGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/goals"})
@CrossOrigin("*")
public class DeviceGoalGroupController implements CrudController<UIDeviceGoalGroup, EOGoalGroup, Long>{
	
	
	@Autowired
	private DeviceGoalGroupService clientGoalGroupService;

	@Override
	public CrudService<UIDeviceGoalGroup, EOGoalGroup, Long> getService() {
		return clientGoalGroupService;
	}

}
