package com.brijframework.client.device.service;

import static org.unlimits.rest.constants.RestConstant.ORDER_BY;
import static org.unlimits.rest.constants.RestConstant.SORT_ORDER;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unlimits.rest.crud.mapper.GenericMapper;
import org.unlimits.rest.crud.service.CrudServiceImpl;

import com.brijframework.client.constants.RecordStatus;
import com.brijframework.client.device.mapper.DeviceCoachGroupMapper;
import com.brijframework.client.device.model.UIUnlimitsCoachConversion;
import com.brijframework.client.entities.EOUnlimitsCoachConversion;
import com.brijframework.client.repository.CoachLibararyRepository;

@Service
public class DeviceUnlimitCoachConversionServiceImpl extends CrudServiceImpl<UIUnlimitsCoachConversion, EOUnlimitsCoachConversion, Long> implements DeviceUnlimitCoachConversionService {
	
	private static final String RECORD_STATE = "recordState";
	
	@Autowired
	private CoachLibararyRepository coachLibararyRepository;
	
	@Autowired
	private DeviceCoachGroupMapper deviceCoachLibararyMapper;
	
	@Value("${openapi.service.url}")
	private String serverUrl;
	
	@Override
	public JpaRepository<EOUnlimitsCoachConversion, Long> getRepository() {
		return coachLibararyRepository;
	}

	@Override
	public GenericMapper<EOUnlimitsCoachConversion, UIUnlimitsCoachConversion> getMapper() {
		return deviceCoachLibararyMapper;
	}
	
	@Override
	public List<UIUnlimitsCoachConversion> postFetch(List<EOUnlimitsCoachConversion> findObjects) {
		List<UIUnlimitsCoachConversion> uiObjects = super.postFetch(findObjects);
		uiObjects.sort((op1,op2)->op2.getCoachDate().compareTo(op1.getCoachDate()));
		return uiObjects;
	}
	
	@Override
	public void preFetch(Map<String, List<String>> headers, Map<String, Object> filters) {
		filters.put(RECORD_STATE, RecordStatus.ACTIVETED.getStatusIds());
	}
	
	@Override
	public List<Order> buidOrders(Map<String, Object> sortOrders) {
		sortOrders.put(ORDER_BY, "coachDate");
		sortOrders.put(SORT_ORDER, "desc");
		return super.buidOrders(sortOrders);
	}
	
	@Override
	public void postFetch(EOUnlimitsCoachConversion findObject, UIUnlimitsCoachConversion dtoObject) {
		
	}
}
