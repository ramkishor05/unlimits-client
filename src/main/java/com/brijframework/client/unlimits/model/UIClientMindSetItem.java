package com.brijframework.client.unlimits.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.forgin.model.ResourceFile;

public class UIClientMindSetItem extends UIModel{

	private ResourceFile content;
	
	private String url;
	
	private String name;
	
	private String description;

	public ResourceFile getContent() {
		return content;
	}

	public void setContent(ResourceFile content) {
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
