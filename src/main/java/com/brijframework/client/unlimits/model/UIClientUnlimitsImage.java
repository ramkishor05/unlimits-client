package com.brijframework.client.unlimits.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIClientUnlimitsImage extends UIClientUnlimits {

	private List<UIClientUnlimitsImageItem> imageItems;

	public List<UIClientUnlimitsImageItem> getImageItems() {
		return imageItems;
	}

	public void setImageItems(List<UIClientUnlimitsImageItem> imageItems) {
		this.imageItems = imageItems;
	}
}
