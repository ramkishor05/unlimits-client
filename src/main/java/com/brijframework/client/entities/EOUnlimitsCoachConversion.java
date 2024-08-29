package com.brijframework.client.entities;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "EOUNLIMIT_COACH_CONVERSION")
public class EOUnlimitsCoachConversion extends EOEntityObject {

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
	
	@JoinColumn(name = CUST_BUSINESS_APP_ID, nullable = false)
	@ManyToOne
	private EOCustBusinessApp custBusinessApp;

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

	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}
}
