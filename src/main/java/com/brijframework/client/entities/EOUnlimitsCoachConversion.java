package com.brijframework.client.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "EOUNLIMIT_COACH_CONVERSION")
public class EOUnlimitsCoachConversion extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "COACH_DATE")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date coachDate;
	
	@Column(name = "COACH_REQUEST", columnDefinition = "LONGTEXT")
	private String coachRequest;
	
	@Column(name = "COACH_RESPONSE", columnDefinition = "LONGTEXT")
	private String coachResponse;

	public Date getCoachDate() {
		return coachDate;
	}

	public void setCoachDate(Date coachDate) {
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
