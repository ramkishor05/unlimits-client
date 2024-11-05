package com.brijframework.client.device.service;

import java.util.List;
import java.util.Map;

import com.brijframework.client.device.model.UIDeviceUnlimits;
import com.brijframework.client.device.model.UIDeviceUnlimitsVisualize;
import com.brijframework.client.device.model.UIDeviceVisualizeRequest;

public interface DeviceVisualizeService extends DeviceUnlimitsVisualizeService{
	
	List<UIDeviceUnlimits> findAllUnlimits(Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions);

	UIDeviceUnlimits findUnlimits(UIDeviceVisualizeRequest clientVisualizeRequest, Map<String, List<String>> headers,
			Map<String, Object> filters, Map<String, Object> actions);
	
	UIDeviceUnlimitsVisualize requestUnlimits(UIDeviceVisualizeRequest clientVisualizeRequest, Map<String, List<String>> headers, Map<String, Object> filters, Map<String, Object> actions);

}
