/**
 * 
 */
package com.brijframework.client.unlimits.device.controller;

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

import com.brijframework.client.unlimits.device.service.DeviceClientUnlimitsImageService;
import com.brijframework.client.unlimits.entities.EOCustUnlimitsImage;
import com.brijframework.client.unlimits.model.UICustUnlimitsImage;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({ "/api/device/client/unlimits/image", "/api/client/unlimits/image"})
@CrossOrigin("*")
public class DeviceClientUnlimitsImageController implements CrudController<UICustUnlimitsImage, EOCustUnlimitsImage, Long>{
	
	@Autowired
	private DeviceClientUnlimitsImageService clientUnlimitsImageService;

	@Override
	public CrudService<UICustUnlimitsImage, EOCustUnlimitsImage, Long> getService() {
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
