package com.brijframework.client.device.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceUnlimitsImage extends UIDeviceUnlimits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIDeviceUnlimitsImageItem> imageItems;

	public List<UIDeviceUnlimitsImageItem> getImageItems() {
		return imageItems;
	}

	public void setImageItems(List<UIDeviceUnlimitsImageItem> imageItems) {
		this.imageItems = imageItems;
	}
}
