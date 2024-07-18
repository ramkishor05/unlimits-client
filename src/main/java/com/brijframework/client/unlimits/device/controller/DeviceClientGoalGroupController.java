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

import com.brijframework.client.unlimits.device.service.DeviceClientGoalGroupService;
import com.brijframework.client.unlimits.entities.EOClientGoalGroup;
import com.brijframework.client.unlimits.model.UIClientGoalGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/goals"})
@CrossOrigin("*")
public class DeviceClientGoalGroupController implements CrudController<UIClientGoalGroup, EOClientGoalGroup, Long>{
	
	
	@Autowired
	private DeviceClientGoalGroupService clientGoalGroupService;

	@Override
	public CrudService<UIClientGoalGroup, EOClientGoalGroup, Long> getService() {
		return clientGoalGroupService;
	}

}
