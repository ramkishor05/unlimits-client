package com.brijframework.client.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceUnlimitsTag extends UIDeviceUnlimits{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIDeviceUnlimitsTagItem> tagItems;

	public List<UIDeviceUnlimitsTagItem> getTagItems() {
		return tagItems;
	}

	public void setTagItems(List<UIDeviceUnlimitsTagItem> tagItems) {
		this.tagItems = tagItems;
	}
}
