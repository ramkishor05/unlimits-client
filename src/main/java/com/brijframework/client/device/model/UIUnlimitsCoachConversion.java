package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

public class UIUnlimitsCoachConversion extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String coachDate;
	private String coachRequest;
	private String coachResponse;

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

	public String getCoachResponse() {
		return coachResponse;
	}

	public void setCoachResponse(String coachResponse) {
		this.coachResponse = coachResponse;
	}

}
