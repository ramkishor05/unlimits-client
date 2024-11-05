package com.brijframework.client.global.model;

import java.util.ArrayList;
import java.util.List;

import com.brijframework.client.forgin.model.ResourceFileModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalReProgramGroup extends UIGlobalModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	private String description;

	private Long reprogramId;

	private ResourceFileModel content;

	private String url;

	private List<UIGlobalReProgramItem> reprograms;

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

	public List<UIGlobalReProgramItem> getReprograms() {
		if (reprograms == null) {
			reprograms = new ArrayList<UIGlobalReProgramItem>();
		}
		return reprograms;
	}

	public void setReprograms(List<UIGlobalReProgramItem> reprograms) {
		this.reprograms = reprograms;
	}

}
