/**
 * 
 */
package com.brijframework.client.unlimits.entities;

import java.util.Date;

import com.brijframework.client.entities.EOCustObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_GOAL_ITEM")
public class EOClientGoalItem extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TO_DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name ="STATUS")
	private Boolean status;
	
	@Column(name ="TITLE")
	private String title;

	@JoinColumn(name = "GROUP_ID", nullable = false, referencedColumnName = "id")
	@ManyToOne
	private EOClientGoalGroup group;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public EOClientGoalGroup getGroup() {
		return group;
	}

	public void setGroup(EOClientGoalGroup group) {
		this.group = group;
	}

}
