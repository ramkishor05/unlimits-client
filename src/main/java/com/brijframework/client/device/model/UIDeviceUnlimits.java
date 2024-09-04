package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.constants.UnlimitsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceUnlimits extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String date;

	private String name;
	
	private UnlimitsType type;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
