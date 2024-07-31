package com.brijframework.client.forgin.model;

import java.io.Serializable;

public class PromptLibarary implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type;

	private Long subCategoryId;

	private Long tenureId;

	private String idenNo;

	private String title;
	
	private String description;

	private String name;

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getTenureId() {
		return tenureId;
	}

	public void setTenureId(Long tenureId) {
		this.tenureId = tenureId;
	}
}
