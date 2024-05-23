/**
 * 
 */
package com.brijframework.client.unlimits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.unlimits.entities.EOClientUnlimitsExample;
import com.brijframework.client.unlimits.model.UIClientUnlimitsExample;
import com.brijframework.client.unlimits.service.ClientUnlimitsExampleService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/client/unlimits/example")
@CrossOrigin("*")
public class ClientUnlimitsExampleController extends CrudController<UIClientUnlimitsExample, EOClientUnlimitsExample, Long>{
	
	@Autowired
	private ClientUnlimitsExampleService clientUnlimitsExampleService;

	@Override
	public CrudService<UIClientUnlimitsExample, EOClientUnlimitsExample, Long> getService() {
		return clientUnlimitsExampleService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(clientUnlimitsExampleService.getCurrent(headers));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
