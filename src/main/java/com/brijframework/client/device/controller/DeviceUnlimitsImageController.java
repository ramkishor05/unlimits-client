/**
 * 
 */
package com.brijframework.client.device.controller;

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

import com.brijframework.client.device.model.UIDeviceUnlimitsImage;
import com.brijframework.client.device.service.DeviceUnlimitsImageService;
import com.brijframework.client.entities.EOUnlimitsImage;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({ "/api/device/unlimits/image"})
@CrossOrigin("*")
public class DeviceUnlimitsImageController implements CrudController<UIDeviceUnlimitsImage, EOUnlimitsImage, Long>{
	
	@Autowired
	private DeviceUnlimitsImageService clientUnlimitsImageService;

	@Override
	public CrudService<UIDeviceUnlimitsImage, EOUnlimitsImage, Long> getService() {
		return clientUnlimitsImageService;
	}
	
    @GetMapping("/current")
	public Response<Object> getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response<Object> response=new Response<Object>();
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
