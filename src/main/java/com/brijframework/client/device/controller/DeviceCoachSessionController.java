package com.brijframework.client.device.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.beans.QueryRequest;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CQRSController;
import org.unlimits.rest.crud.controller.CrudController;

import com.brijframework.client.device.model.UIDeviceCoachSession;
import com.brijframework.client.device.service.DeviceCoachSessionService;
import com.brijframework.client.entities.EOCoachSession;


@RestController
@RequestMapping("/api/device/coach/conversion/session")
@CrossOrigin("*")
public class DeviceCoachSessionController implements CrudController<UIDeviceCoachSession, EOCoachSession, Long>{

	private String FIND_BY_DATERANGE="findAllByCoachDateDateRange";

	@Autowired
	private DeviceCoachSessionService deviceCoachSessionService;

	@Override
	public DeviceCoachSessionService getService() {
		return deviceCoachSessionService;
	}
	
	@GetMapping("/daterange")
    Response<Object> findAllByDateRange(@RequestHeader(required =false)  MultiValueMap<String,String> headers ,@RequestParam String startDate, @RequestParam String endDate, WebRequest webRequest){
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> sortOrders = CQRSController.getSortings(webRequest);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		QueryRequest queryRequest=new QueryRequest(params, headers, sortOrders, filters, FIND_BY_DATERANGE, "/daterange");
		Response<Object> response=new Response<Object>();
		try {
			response.setData(customizedResponse(getService().findAllByCoachDateDateRange(startDate, endDate, headers, sortOrders, filters), queryRequest));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	} 
	
	@GetMapping("/lastday")
    public Response<Object> today(@RequestHeader(required =false)  MultiValueMap<String,String> headers , WebRequest webRequest){
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> sortOrders = CQRSController.getSortings(webRequest);
		Map<String, Object> params=new HashMap<String, Object>();
		QueryRequest queryRequest=new QueryRequest(params, headers, sortOrders, filters, FIND_BY_DATERANGE, "/lastday");
		Response<Object> response=new Response<Object>();
		try {
			response.setData(customizedResponse(getService().findLastCoach(), queryRequest));
			response.setSuccess(SUCCESS);
			response.setMessage(SUCCESSFULLY_PROCCEED);
			return response;
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	} 
}
