package com.brijframework.client.unlimits.device.service;

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.unlimits.model.UIClientVisualize;

public interface DeviceClientVisualizeService {
	
	public UIClientVisualize  getVisualize(Integer year, UnlimitsType type, Long unlimitId) ;
}
