/**
 * 
 */
package com.brijframework.client.entities;

import java.util.Date;

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
public class EOGoalItem extends EOEntityObject {

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
	private EOGoalGroup group;

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

	public EOGoalGroup getGroup() {
		return group;
	}

	public void setGroup(EOGoalGroup group) {
		this.group = group;
	}

}
