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
	private Long unlimitId;

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

	public Long getUnlimitId() {
		return unlimitId;
	}

	public void setUnlimitId(Long unlimitId) {
		this.unlimitId = unlimitId;
	}

	@JsonIgnore
	public UnlimitsType getUnlmitsType() {
		return UnlimitsType.findByType(type);
	}

}