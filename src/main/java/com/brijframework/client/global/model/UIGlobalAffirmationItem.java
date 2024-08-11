package com.brijframework.client.global.model;

import com.brijframework.client.forgin.model.ResourceFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalAffirmationItem extends UIGlobalModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ResourceFile content;
	
	private String url;
	
	private String name;
	
	private String description;

	public ResourceFile getContent() {
		return content;
	}

	public void setcontent(ResourceFile content) {
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
