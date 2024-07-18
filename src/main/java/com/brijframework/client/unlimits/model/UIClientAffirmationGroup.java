package com.brijframework.client.unlimits.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIClientAffirmationGroup extends UIModel{

	private String name;

	private String description;

	private List<UIClientAffirmationItem> affirmations;

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

	public List<UIClientAffirmationItem> getAffirmations() {
		return affirmations;
	}

	public void setAffirmations(List<UIClientAffirmationItem> Affirmations) {
		this.affirmations = Affirmations;
	}
	
}
