/**
 * 
 */
package com.brijframework.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.entities.EOClientUnlimits;
import com.brijframework.client.model.ClientUnlimitsDTO;
import com.brijframework.client.service.ClientUnlimitsService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/client/unlimits")
@CrossOrigin("*")
public class ClientUnlimitsController extends CrudController<ClientUnlimitsDTO, EOClientUnlimits, Long>{
	
	@Autowired
	private ClientUnlimitsService clientUnlimitsService;

	@Override
	public CrudService<ClientUnlimitsDTO, EOClientUnlimits, Long> getService() {
		return clientUnlimitsService;
	}
	
}
