package com.brijframework.client.unlimits.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.forgin.model.UIResource;

public class UIClientReProgramItem extends UIModel {

	private UIResource resource;

	private String url;

	private String name;

	private String description;

	public UIResource getResource() {
		return resource;
	}

	public void setResource(UIResource resource) {
		this.resource = resource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

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

}
