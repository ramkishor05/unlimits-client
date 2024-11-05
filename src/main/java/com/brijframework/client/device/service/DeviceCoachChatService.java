package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceCoachChat;
import com.brijframework.client.entities.EOCoachChat;


public interface DeviceCoachChatService extends CrudService<UIDeviceCoachChat, EOCoachChat, Long>{

	UIDeviceCoachChat findLastChat(Long sessionId, Map<String, List<String>> headers,Map<String, Object> filters, Map<String, Object> actions);

	UIDeviceCoachChat findFirstChat(Long sessionId, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions);

	List<UIDeviceCoachChat> findAllByCoachDateDateRange(Long sessionId, String startDate, String endDate,
			Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> sortOrders);

}
