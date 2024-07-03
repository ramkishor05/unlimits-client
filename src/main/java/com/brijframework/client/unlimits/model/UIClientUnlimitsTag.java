package com.brijframework.client.unlimits.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIClientUnlimitsTag extends UIClientUnlimits{

	private List<UIClientUnlimitsTagItem> tagItems;

	public List<UIClientUnlimitsTagItem> getTagItems() {
		return tagItems;
	}

	public void setTagItems(List<UIClientUnlimitsTagItem> tagItems) {
		this.tagItems = tagItems;
	}
}
