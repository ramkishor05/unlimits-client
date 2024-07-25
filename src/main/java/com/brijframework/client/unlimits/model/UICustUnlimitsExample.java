package com.brijframework.client.unlimits.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UICustUnlimitsExample extends UIClientUnlimits{
	
	private Long exampleId;

	private List<UIClientUnlimitsExampleItem> exampleItems;

	public Long getExampleId() {
		return exampleId;
	}

	public void setExampleId(Long exampleId) {
		this.exampleId = exampleId;
	}

	public List<UIClientUnlimitsExampleItem> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<UIClientUnlimitsExampleItem> exampleItems) {
		this.exampleItems = exampleItems;
	}

}
