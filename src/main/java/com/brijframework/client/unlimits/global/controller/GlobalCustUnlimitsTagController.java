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

import com.brijframework.client.unlimits.entities.EOCustUnlimitsTag;
import com.brijframework.client.unlimits.global.service.GlobalCustUnlimitsTagService;
import com.brijframework.client.unlimits.model.UICustUnlimitsTag;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/unlimits/tag")
@CrossOrigin("*")
@Hidden
public class GlobalCustUnlimitsTagController implements CrudController<UICustUnlimitsTag, EOCustUnlimitsTag, Long>{
	
	@Autowired
	private GlobalCustUnlimitsTagService globalCustUnlimitsTagService;

	@Override
	public CrudService<UICustUnlimitsTag, EOCustUnlimitsTag, Long> getService() {
		return globalCustUnlimitsTagService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(globalCustUnlimitsTagService.getCurrent(headers));
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
