package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.constants.UnlimitsType;

public class UIDeviceVisualizeRequest extends UIModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer year;
	private UnlimitsType type;
	private Long unlimitId;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public UnlimitsType getType() {
		if(type==null) {
			type=UnlimitsType.WORDS;
		}
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