package com.brijframework.client.global.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalUnlimitsTag extends UIGlobalUnlimits{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIGlobalUnlimitsTagItem> tagItems;

	public List<UIGlobalUnlimitsTagItem> getTagItems() {
		return tagItems;
	}

	public void setTagItems(List<UIGlobalUnlimitsTagItem> tagItems) {
		this.tagItems = tagItems;
	}
}
