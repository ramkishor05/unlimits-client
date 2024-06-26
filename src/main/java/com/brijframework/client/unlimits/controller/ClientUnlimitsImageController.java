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

import com.brijframework.client.unlimits.entities.EOClientUnlimitsImage;
import com.brijframework.client.unlimits.model.UIClientUnlimitsImage;
import com.brijframework.client.unlimits.service.ClientUnlimitsImageService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/client/unlimits/image")
@CrossOrigin("*")
public class ClientUnlimitsImageController extends CrudController<UIClientUnlimitsImage, EOClientUnlimitsImage, Long>{
	
	@Autowired
	private ClientUnlimitsImageService clientUnlimitsImageService;

	@Override
	public CrudService<UIClientUnlimitsImage, EOClientUnlimitsImage, Long> getService() {
		return clientUnlimitsImageService;
	}
	
    @GetMapping("/current")
	public Response getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response response=new Response();
		try {
			response.setData(clientUnlimitsImageService.getCurrent(headers));
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
