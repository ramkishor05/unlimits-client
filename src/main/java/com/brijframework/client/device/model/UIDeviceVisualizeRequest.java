package com.brijframework.client.device.model;

import com.brijframework.client.constants.UnlimitsType;

import jakarta.validation.constraints.NotNull;

public class UIDeviceVisualizeRequest {
	
	@NotNull
	private Integer year;
	@NotNull
	private UnlimitsType type;
	@NotNull
	private Long unlimitId;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public UnlimitsType getType() {
		return type;
	}

	public void setType(UnlimitsType type) {
		this.type = type;
	}

	public Long getUnlimitId() {
		return unlimitId;
	}

	public void setUnlimitId(Long unlimitId) {
		this.unlimitId = unlimitId;
	}

	@Override
	public String toString() {
		return "UIVisualizeRequest [year=" + year + ", type=" + type + ", unlimitId=" + unlimitId + "]";
	}

	
}