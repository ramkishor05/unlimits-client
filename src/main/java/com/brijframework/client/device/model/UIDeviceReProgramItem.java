package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.forgin.model.ResourceFileModel;

public class UIDeviceReProgramItem extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceFileModel content;

	private String url;

	private String name;

	private String description;

	public ResourceFileModel getContent() {
		return content;
	}

	public void setContent(ResourceFileModel content) {
		this.content = content;
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
