package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

public class UIDeviceCoachItem extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String coachDate;
	private String coachRequest;
	private String coachrResponse;

	public String getCoachDate() {
		return coachDate;
	}

	public void setCoachDate(String coachDate) {
		this.coachDate = coachDate;
	}

	public String getCoachRequest() {
		return coachRequest;
	}

	public void setCoachRequest(String coachRequest) {
		this.coachRequest = coachRequest;
	}

	public String getCoachrResponse() {
		return coachrResponse;
	}

	public void setCoachrResponse(String coachrResponse) {
		this.coachrResponse = coachrResponse;
	}

}
