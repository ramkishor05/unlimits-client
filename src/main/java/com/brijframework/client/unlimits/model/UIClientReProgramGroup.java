package com.brijframework.client.unlimits.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIClientReProgramGroup extends UIModel {

	private String name;

	private String description;

	private List<UIClientReProgramItem> reprograms;

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

	public List<UIClientReProgramItem> getReprograms() {
		return reprograms;
	}

	public void setReprograms(List<UIClientReProgramItem> reprograms) {
		this.reprograms = reprograms;
	}

}
