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

import com.brijframework.client.unlimits.entities.EOClientUnlimitsTag;
import com.brijframework.client.unlimits.global.service.GlobalClientUnlimitsTagService;
import com.brijframework.client.unlimits.model.UIClientUnlimitsTag;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/client/unlimits/tag")
@CrossOrigin("*")
public class GlobalClientUnlimitsTagController extends CrudController<UIClientUnlimitsTag, EOClientUnlimitsTag, Long>{
	
	@Autowired
	private GlobalClientUnlimitsTagService clientUnlimitsTagService;

	@Override
	public CrudService<UIClientUnlimitsTag, EOClientUnlimitsTag, Long> getService() {
		return clientUnlimitsTagService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(clientUnlimitsTagService.getCurrent(headers));
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