package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIUnlimitsCoachConversion;
import com.brijframework.client.entities.EOUnlimitsCoachConversion;


public interface DeviceUnlimitCoachConversionService extends CrudService<UIUnlimitsCoachConversion, EOUnlimitsCoachConversion, Long>{

	List<UIUnlimitsCoachConversion> findAllByCoachDateDateRange(String startDate, String endDate,
			MultiValueMap<String, String> headers, Map<String, Object> sortOrders, Map<String, Object> filters);

	List<UIUnlimitsCoachConversion> findLastCoach();

	List<UIUnlimitsCoachConversion> findYesterdayCoach();

}
