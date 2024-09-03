package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import org.unlimits.rest.crud.service.CrudService;

import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.entities.EOUnlimitsVisualize;

public interface DeviceVisualizeService extends CrudService<UIDeviceUnlimitsVisualize, EOUnlimitsVisualize, Long>{
	
	List<UIDeviceUnlimits> findAllDeviceUnlimits(Map<String, List<String>> headers, Map<String, Object> filters);
	
	UIDeviceUnlimitsVisualize request(UIDeviceVisualizeRequest clientVisualizeRequest, Map<String, List<String>> headers);

}
