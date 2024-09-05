/**
 * 
 */
package com.brijframework.client.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "EOCLIENT_COMMITMENT_ITEM")
public class EOCommitmentItem extends EOEntityObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TO_DATE")
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date date;

	@Column(name ="TITLE")
	private String title;
	
	@Column(name ="STATUS")
	private Boolean status;

	@JoinColumn(name = "GROUP_ID", nullable = false, referencedColumnName = "id")
	@ManyToOne
	private EOCommitmentGroup group;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public EOCommitmentGroup getGroup() {
		return group;
	}

	public void setGroup(EOCommitmentGroup group) {
		this.group = group;
	}

}
