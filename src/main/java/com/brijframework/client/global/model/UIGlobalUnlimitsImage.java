package com.brijframework.client.global.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalUnlimitsImage extends UIGlobalUnlimits {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIGlobalUnlimitsImageItem> imageItems;

	public List<UIGlobalUnlimitsImageItem> getImageItems() {
		return imageItems;
	}

	public void setImageItems(List<UIGlobalUnlimitsImageItem> imageItems) {
		this.imageItems = imageItems;
	}
}
