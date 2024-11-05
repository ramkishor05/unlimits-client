package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.constants.UnlimitsType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UIDeviceVisualizeRequest extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer year;
	private String type;
	private Long unlimitsId;
	private Long subCategoryId;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUnlimitsId() {
		return unlimitsId;
	}

	public void setUnlimitsId(Long unlimitsId) {
		this.unlimitsId = unlimitsId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	@JsonIgnore
	public UnlimitsType getUnlmitsType() {
		return UnlimitsType.findByType(type);
	}

}