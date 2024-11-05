package com.brijframework.client.global.model;

import com.brijframework.client.forgin.model.ResourceFileModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalReProgramItem extends UIGlobalModel {
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
