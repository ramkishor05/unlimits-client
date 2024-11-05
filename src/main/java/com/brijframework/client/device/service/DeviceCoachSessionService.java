package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceCoachSession;
import com.brijframework.client.entities.EOCoachSession;


public interface DeviceCoachSessionService extends CrudService<UIDeviceCoachSession, EOCoachSession, Long>{

	List<UIDeviceCoachSession> findAllByCoachDateDateRange(String startDate, String endDate,
			MultiValueMap<String, String> headers, Map<String, Object> sortOrders, Map<String, Object> filters);

	List<UIDeviceCoachSession> findLastCoach();

	List<UIDeviceCoachSession> findYesterdayCoach();

}
