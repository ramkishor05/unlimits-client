package com.brijframework.client.visualise.model;

import java.util.List;

public class UIClientVisualiseImage extends UIClientVisualise {

	private List<UIClientVisualiseImageItem> imageItems;

	public List<UIClientVisualiseImageItem> getImageItems() {
		return imageItems;
	}

	public void setImageItems(List<UIClientVisualiseImageItem> imageItems) {
		this.imageItems = imageItems;
	}
}
