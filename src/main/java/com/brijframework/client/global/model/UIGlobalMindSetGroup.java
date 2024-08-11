package com.brijframework.client.global.model;

import java.util.List;

import com.brijframework.client.forgin.model.ResourceFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalMindSetGroup extends UIGlobalModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	private String description;
	
	private Long mindsetId;

	private ResourceFile content;
	
	private String url;

	private List<UIGlobalMindSetItem> mindSets;

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

	public Long getMindsetId() {
		return mindsetId;
	}

	public void setMindsetId(Long mindsetId) {
		this.mindsetId = mindsetId;
	}

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

	public List<UIGlobalMindSetItem> getMindSets() {
		return mindSets;
	}

	public void setMindSets(List<UIGlobalMindSetItem> mindSets) {
		this.mindSets = mindSets;
	}
	
}
