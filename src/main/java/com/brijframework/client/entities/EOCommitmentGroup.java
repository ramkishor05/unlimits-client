/**
 * 
 */
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

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_COMMITMENT_GROUP")
public class EOCommitmentGroup extends EOCustObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "COMMITMENT_DATE")
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date commitmentDate;

	@Column(name = "COMMITMENT_NAME")
	private String commitmentName;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOCommitmentItem> commitments;

	public Date getCommitmentDate() {
		return commitmentDate;
	}

	public void setCommitmentDate(Date commitmentDate) {
		this.commitmentDate = commitmentDate;
	}

	public String getCommitmentName() {
		return commitmentName;
	}

	public void setCommitmentName(String commitmentName) {
		this.commitmentName = commitmentName;
	}

	public List<EOCommitmentItem> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<EOCommitmentItem> commitments) {
		this.commitments = commitments;
	}

}
