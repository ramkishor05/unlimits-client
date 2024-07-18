/**
 * 
 */
package com.brijframework.client.unlimits.entities;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOCustObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_COMMITMENT_GROUP")
public class EOClientCommitmentGroup extends EOCustObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "COMMITMENT_DATE")
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date commitmentDate;

	@Column(name = "COMMITMENT_NAME")
	private String CommitmentName;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOClientCommitmentItem> commitments;

	@JoinColumn(name = CUST_BUSINESS_APP_ID, nullable = false)
	@ManyToOne
	private EOCustBusinessApp custBusinessApp;

	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}

	public Date getCommitmentDate() {
		return commitmentDate;
	}

	public void setCommitmentDate(Date commitmentDate) {
		this.commitmentDate = commitmentDate;
	}

	public String getCommitmentName() {
		return CommitmentName;
	}

	public void setCommitmentName(String commitmentName) {
		CommitmentName = commitmentName;
	}

	public List<EOClientCommitmentItem> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<EOClientCommitmentItem> commitments) {
		this.commitments = commitments;
	}

}
