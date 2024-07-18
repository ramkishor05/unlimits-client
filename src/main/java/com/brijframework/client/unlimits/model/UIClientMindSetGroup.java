package com.brijframework.client.unlimits.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIClientMindSetGroup extends UIModel{

	private String name;

	private String description;

	private List<UIClientMindSetItem> mindSets;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UIClientMindSetItem> getMindSets() {
		return mindSets;
	}

	public void setMindSets(List<UIClientMindSetItem> mindSets) {
		this.mindSets = mindSets;
	}
	
}
