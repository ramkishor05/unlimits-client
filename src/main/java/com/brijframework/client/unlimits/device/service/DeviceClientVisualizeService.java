package com.brijframework.client.unlimits.device.service;

import java.util.List;
import java.util.Map;

import com.brijframework.client.unlimits.model.UIClientUnlimits;
import com.brijframework.client.unlimits.model.UIClientVisualizeRequest;
import com.brijframework.client.unlimits.model.UIClientVisualizeResponse;

public interface DeviceClientVisualizeService {
	
	List<UIClientUnlimits> findAll(Map<String, List<String>> headers, Map<String, Object> filters);
	
	UIClientVisualizeResponse add(UIClientVisualizeRequest clientVisualizeRequest);

}
