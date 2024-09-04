/**
 * 
 */
package com.brijframework.client.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CQRSController;
import org.unlimits.rest.crud.controller.CrudController;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.device.service.DeviceVisualizeService;
import com.brijframework.client.entities.EOUnlimitsVisualize;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({ "/api/device/visualize" })
@CrossOrigin("*")
public class DeviceVisualizeController implements CrudController<UIDeviceUnlimitsVisualize, EOUnlimitsVisualize, Long> {

	@Autowired
	private DeviceVisualizeService clientVisualizeService;
	
	@Override
	public CrudService<UIDeviceUnlimitsVisualize, EOUnlimitsVisualize, Long> getService() {
		return clientVisualizeService;
	}

	@PostMapping("/request")
	public Response<Object> add(@RequestBody  UIDeviceVisualizeRequest clientVisualizeRequest, @RequestHeader MultiValueMap<String,String> headers) {
		Response<Object> response=new Response<Object>();
		try {
			response.setData(clientVisualizeService.request(clientVisualizeRequest, headers));
			response.setSuccess(CQRSController.SUCCESS);
			response.setMessage(CQRSController.SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(CQRSController.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/unlimits")
	public Response<Object> findAllDeviceUnlimits(@RequestHeader(required = false) MultiValueMap<String, String> headers,
			WebRequest webRequest) {
		Response<Object> response=new Response<Object>();
		try {
			response.setData(clientVisualizeService.findAllDeviceUnlimits(headers, CQRSController.getfilters(webRequest)));
			response.setSuccess(CQRSController.SUCCESS);
			response.setMessage(CQRSController.SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(CQRSController.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

}
