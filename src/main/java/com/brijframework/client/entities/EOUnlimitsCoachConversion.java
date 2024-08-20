package com.brijframework.client.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOUNLIMIT_COACH_CONVERSION")
public class EOUnlimitsCoachConversion extends EOEntityObject {

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
