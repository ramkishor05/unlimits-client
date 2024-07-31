/**
 * 
 */
package com.brijframework.client.unlimits.global.controller;

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

import com.brijframework.client.unlimits.entities.EOCustUnlimitsExample;
import com.brijframework.client.unlimits.global.service.GlobalCustUnlimitsExampleService;
import com.brijframework.client.unlimits.model.UICustUnlimitsExample;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/unlimits/example")
@CrossOrigin("*")
@Hidden
public class GlobalCustUnlimitsExampleController implements CrudController<UICustUnlimitsExample, EOCustUnlimitsExample, Long>{
	
	@Autowired
	private GlobalCustUnlimitsExampleService globalCustUnlimitsExampleService;

	@Override
	public CrudService<UICustUnlimitsExample, EOCustUnlimitsExample, Long> getService() {
		return globalCustUnlimitsExampleService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(globalCustUnlimitsExampleService.getCurrent(headers));
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