package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

public class UIDeviceGoalItem extends UIModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String toDate;
	
	private String title;
	
	private Boolean status;

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
