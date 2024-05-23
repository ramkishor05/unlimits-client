package com.brijframework.client.visualise.model;

import java.util.List;

public class UIClientVisualiseTag extends UIClientVisualise{

	private List<UIClientVisualiseTagItem> tagItems;

	public List<UIClientVisualiseTagItem> getTagItems() {
		return tagItems;
	}

	public void setTagItems(List<UIClientVisualiseTagItem> tagItems) {
		this.tagItems = tagItems;
	}
}
