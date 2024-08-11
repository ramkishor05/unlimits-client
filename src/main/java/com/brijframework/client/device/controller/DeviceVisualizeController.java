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

import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.device.service.DeviceVisualizeService;

/**
 *  @author omnie
 */
@RestController
@RequestMapping({ "/api/device/visualize" })
@CrossOrigin("*")
public class DeviceVisualizeController {

	@Autowired
	private DeviceVisualizeService clientVisualizeService;

	@PostMapping
	public Response<Object>  add(@RequestBody  UIDeviceVisualizeRequest clientVisualizeRequest) {
		Response<Object> response=new Response<Object>();
		try {
			response.setData(clientVisualizeService.add(clientVisualizeRequest));
			response.setSuccess(CQRSController.SUCCESS);
			response.setMessage(CQRSController.SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			response.setSuccess(CQRSController.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@GetMapping
	public Response<Object> findAll(@RequestHeader(required = false) MultiValueMap<String, String> headers,
			WebRequest webRequest) {
		Response<Object> response=new Response<Object>();
		try {
			response.setData(clientVisualizeService.findAll(headers, CQRSController.getfilters(webRequest)));
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
