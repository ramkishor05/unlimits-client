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

import com.brijframework.client.device.model.UIDeviceCommitmentGroup;
import com.brijframework.client.device.service.DeviceCommitmentService;
import com.brijframework.client.entities.EOCommitmentGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/commitments"})
@CrossOrigin("*")
public class DeviceCommitmentController implements CrudController<UIDeviceCommitmentGroup, EOCommitmentGroup, Long>{
	
	@Autowired
	private DeviceCommitmentService clientCommitmentService;

	@Override
	public CrudService<UIDeviceCommitmentGroup, EOCommitmentGroup, Long> getService() {
		return clientCommitmentService;
	}

}
