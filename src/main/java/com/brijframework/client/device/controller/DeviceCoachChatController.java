package com.brijframework.client.device.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.unlimits.rest.crud.beans.QueryRequest;
import org.unlimits.rest.crud.beans.Response;
import org.unlimits.rest.crud.controller.CQRSController;
import org.unlimits.rest.crud.controller.CrudController;

import com.brijframework.client.device.model.UIDeviceCoachChat;
import com.brijframework.client.device.service.DeviceCoachChatService;
import com.brijframework.client.entities.EOCoachChat;


@RestController
@RequestMapping("/api/device/coach/conversion/chat")
@CrossOrigin("*")
public class DeviceCoachChatController implements CrudController<UIDeviceCoachChat, EOCoachChat, Long>{

	private String FIND_BY_DATERANGE="findAllByCoachDateDateRange";

	@Autowired
	private DeviceCoachChatService coachChatService;

	@Override
	public DeviceCoachChatService getService() {
		return coachChatService;
	}
	
	@GetMapping("/daterange/{sessionId}")
    Response<Object> findAllByDateRange(@PathVariable Long sessionId, @RequestHeader(required =false)  Map<String, List<String>> headers ,@RequestParam String startDate, @RequestParam String endDate, WebRequest webRequest){
		Map<String, Object> filters = CQRSController.getfilters(webRequest);
		Map<String, Object> sortOrders = CQRSController.getSortings(webRequest);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		QueryRequest queryRequest=new QueryRequest(params, headers, sortOrders, filters, FIND_BY_DATERANGE, "/daterange");
		Response<Object> response=new Response<Object>();
		try {
			response.setData(customizedResponse(getService().findAllByCoachDateDateRange(sessionId, startDate, endDate, headers, filters, sortOrders), queryRequest));
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
