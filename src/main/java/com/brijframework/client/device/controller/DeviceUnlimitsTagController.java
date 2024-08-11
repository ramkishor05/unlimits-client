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

import com.brijframework.client.device.model.UIDeviceUnlimitsTag;
import com.brijframework.client.device.service.DeviceUnlimitsTagService;
import com.brijframework.client.entities.EOUnlimitsTag;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({"/api/device/unlimits/tag"})
@CrossOrigin("*")
public class DeviceUnlimitsTagController implements CrudController<UIDeviceUnlimitsTag, EOUnlimitsTag, Long>{
	
	@Autowired
	private DeviceUnlimitsTagService clientUnlimitsTagService;

	@Override
	public CrudService<UIDeviceUnlimitsTag, EOUnlimitsTag, Long> getService() {
		return clientUnlimitsTagService;
	}
	
    @GetMapping("/current")
	public Response<Object> getCuurent(@RequestHeader(required =false)  MultiValueMap<String,String> headers){
    	Response<Object> response=new Response<Object>();
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
