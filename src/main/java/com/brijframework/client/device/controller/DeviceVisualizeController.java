/**
 * 
 */
package com.brijframework.client.device.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@Qualifier("DeviceVisualizeService")
	private DeviceVisualizeService clientVisualizeService;
	
	@Override
	public CrudService<UIDeviceUnlimitsVisualize, EOUnlimitsVisualize, Long> getService() {
		return clientVisualizeService;
	}

	@PostMapping("/request")
	public Response<Object> requestUnlimits(@RequestBody  UIDeviceVisualizeRequest clientVisualizeRequest, @RequestHeader MultiValueMap<String,String> headers, WebRequest webRequest) {
		Response<Object> response=new Response<Object>();
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> actions = CQRSController.getActions(webRequest);
		try {
			response.setData(clientVisualizeService.requestUnlimits(clientVisualizeRequest, headers, filters, actions));
			response.setSuccess(CQRSController.SUCCESS);
			response.setMessage(CQRSController.SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(CQRSController.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	@GetMapping("/find/unlimits")
	public Response<Object> findUnlimits(@RequestParam Long unlimitsId, @RequestParam Long subCategoryId, @RequestParam  String type,
			@RequestHeader MultiValueMap<String,String> headers, WebRequest webRequest) {
		UIDeviceVisualizeRequest clientVisualizeRequest=new UIDeviceVisualizeRequest();
		clientVisualizeRequest.setType(type);
		clientVisualizeRequest.setUnlimitsId(unlimitsId);
		clientVisualizeRequest.setSubCategoryId(subCategoryId);
		Response<Object> response=new Response<Object>();
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> actions = CQRSController.getActions(webRequest);
		try {
			response.setData(clientVisualizeService.findUnlimits(clientVisualizeRequest, headers, filters, actions));
			response.setSuccess(CQRSController.SUCCESS);
			response.setMessage(CQRSController.SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(CQRSController.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GetMapping("/all/unlimits")
	public Response<Object> findAllUnlimits(@RequestHeader(required = false) MultiValueMap<String,String> headers,
			WebRequest webRequest) {
		Response<Object> response=new Response<Object>();
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> actions = CQRSController.getActions(webRequest);
		try {
			response.setData(clientVisualizeService.findAllUnlimits(headers,filters, actions));
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
