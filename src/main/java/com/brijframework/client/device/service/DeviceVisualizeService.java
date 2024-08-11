package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;
import com.brijframework.client.device.model.UIDeviceVisualizeResponse;

public interface DeviceVisualizeService {
	
	List<UIDeviceUnlimits> findAll(Map<String, List<String>> headers, Map<String, Object> filters);
	
	UIDeviceVisualizeResponse add(UIDeviceVisualizeRequest clientVisualizeRequest);

}
