package com.brijframework.client.unlimits.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.forgin.model.ResourceFile;

public class UIClientMindSetGroup extends UIModel{

	private String name;

	private String description;
	
	private Long mindsetId;

	private ResourceFile content;
	
	private String url;

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

	public List<UIClientMindSetItem> getMindSets() {
		return mindSets;
	}

	public void setMindSets(List<UIClientMindSetItem> mindSets) {
		this.mindSets = mindSets;
	}
	
}
