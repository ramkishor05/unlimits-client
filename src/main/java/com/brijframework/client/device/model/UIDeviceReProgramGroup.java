package com.brijframework.client.device.model;

import java.util.ArrayList;
import java.util.List;

import com.brijframework.client.forgin.model.ResourceFile;

public class UIDeviceReProgramGroup extends UIDeviceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private String description;

	private Long reprogramId;

	private ResourceFile content;

	private String url;

	private List<UIDeviceReProgramItem> reprograms;

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

	public Long getReprogramId() {
		return reprogramId;
	}

	public void setReprogramId(Long reprogramId) {
		this.reprogramId = reprogramId;
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

	public List<UIDeviceReProgramItem> getReprograms() {
		if (reprograms == null) {
			reprograms = new ArrayList<UIDeviceReProgramItem>();
		}
		return reprograms;
	}

	public void setReprograms(List<UIDeviceReProgramItem> reprograms) {
		this.reprograms = reprograms;
	}

}
