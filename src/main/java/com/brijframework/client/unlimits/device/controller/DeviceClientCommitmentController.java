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

import com.brijframework.client.unlimits.device.service.DeviceClientCommitmentService;
import com.brijframework.client.unlimits.entities.EOClientCommitmentGroup;
import com.brijframework.client.unlimits.model.UIClientCommitmentGroup;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/client/commitments"})
@CrossOrigin("*")
public class DeviceClientCommitmentController implements CrudController<UIClientCommitmentGroup, EOClientCommitmentGroup, Long>{

	
	@Autowired
	private DeviceClientCommitmentService clientCommitmentService;

	@Override
	public CrudService<UIClientCommitmentGroup, EOClientCommitmentGroup, Long> getService() {
		return clientCommitmentService;
	}


}
