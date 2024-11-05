package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

public class UIDeviceCoachChat extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String coachDate;
	private String coachRequest;
	private String coachResponse;
	private Long coachSessionId;

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

	public Long getCoachSessionId() {
		return coachSessionId;
	}

	public void setCoachSessionId(Long coachSessionId) {
		this.coachSessionId = coachSessionId;
	}
	
}
