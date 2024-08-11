package com.brijframework.client.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOCLIENT_COACH_GROUP")
public class EOCoachGroup extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "group")
	private List<EOCoachItem> coachs;

	public List<EOCoachItem> getCoachs() {
		return coachs;
	}

	public void setCoachs(List<EOCoachItem> coachs) {
		this.coachs = coachs;
	}
	
}
