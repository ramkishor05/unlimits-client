/**
 * 
 */
package com.brijframework.client.global.controller;

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

import com.brijframework.client.entities.EOUnlimitsImage;
import com.brijframework.client.global.model.UIGlobalUnlimitsImage;
import com.brijframework.client.global.service.GlobalUnlimitsImageService;

import io.swagger.v3.oas.annotations.Hidden;

/**
 *  @author omnie
 */
@RestController
@RequestMapping(value = "/api/global/unlimits/image")
@CrossOrigin("*")
@Hidden
public class GlobalUnlimitsImageController implements CrudController<UIGlobalUnlimitsImage, EOUnlimitsImage, Long>{
	
	@Autowired
	private GlobalUnlimitsImageService globalUnlimitsImageService;

	@Override
	public CrudService<UIGlobalUnlimitsImage, EOUnlimitsImage, Long> getService() {
		return globalUnlimitsImageService;
	}
	
    @GetMapping("/current")
	public Response<Object> getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response<Object> response=new Response<Object>();
		try {
			response.setData(globalUnlimitsImageService.getCurrent(headers));
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
