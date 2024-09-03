package com.brijframework.client.device.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceUnlimitsTag extends UIDeviceUnlimits{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIDeviceUnlimitsTagItem> tagItems;
	
	private Map<Integer, UIDeviceUnlimitsVisualize> visualizeMap;


	public List<UIDeviceUnlimitsTagItem> getTagItems() {
		return tagItems;
	}

	public void setTagItems(List<UIDeviceUnlimitsTagItem> tagItems) {
		this.tagItems = tagItems;
	}

	public Map<Integer, UIDeviceUnlimitsVisualize> getVisualizeMap() {
		return visualizeMap;
	}

	public void setVisualizeMap(Map<Integer, UIDeviceUnlimitsVisualize> visualizeMap) {
		this.visualizeMap = visualizeMap;
	}
	
}
