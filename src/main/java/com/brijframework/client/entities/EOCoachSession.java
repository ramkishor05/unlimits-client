package com.brijframework.client.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "EOCOACH_CONVERSION_GROUP")
public class EOCoachSession extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "COACH_DATE")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date coachDate;
	
	@OneToMany(mappedBy = "coachSession", cascade = CascadeType.ALL)
	private List<EOCoachChat> coachConversionChats;

	public Date getCoachDate() {
		return coachDate;
	}

	public void setCoachDate(Date coachDate) {
		this.coachDate = coachDate;
	}

	public List<EOCoachChat> getCoachConversionChats() {
		return coachConversionChats;
	}

	public void setCoachConversionChats(List<EOCoachChat> coachConversionChats) {
		this.coachConversionChats = coachConversionChats;
	}

}
