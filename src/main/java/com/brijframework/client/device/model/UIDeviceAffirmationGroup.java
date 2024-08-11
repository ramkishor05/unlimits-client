package com.brijframework.client.device.model;

import java.util.List;

import com.brijframework.client.forgin.model.ResourceFile;

public class UIDeviceAffirmationGroup extends UIDeviceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String description;
	
	private String affirmationDate;

	private Long affirmationId;

	private ResourceFile content;

	private String url;

	private List<UIDeviceAffirmationItem> affirmations;

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

	public Long getAffirmationId() {
		return affirmationId;
	}

	public void setAffirmationId(Long affirmationId) {
		this.affirmationId = affirmationId;
	}

	public String getAffirmationDate() {
		return affirmationDate;
	}

	public void setAffirmationDate(String affirmationDate) {
		this.affirmationDate = affirmationDate;
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

	public List<UIDeviceAffirmationItem> getAffirmations() {
		return affirmations;
	}

	public void setAffirmations(List<UIDeviceAffirmationItem> Affirmations) {
		this.affirmations = Affirmations;
	}

}