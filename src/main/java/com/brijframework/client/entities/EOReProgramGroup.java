package com.brijframework.client.entities;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOCLIENT_REPROGRAM_GROUP")
public class EOReProgramGroup extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "REPROGRAM_DATE")
	private Date reprogramDate;
	
	@Column(name = "REPROGRAM_ID")
	private Long reprogramId;

	@Column(name = "RESOURCE_ID")
	private Long resourceId;

	@Column(name ="URL")
	private String url;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOReProgramItem> reprograms;

	@JoinColumn(name = CUST_BUSINESS_APP_ID, nullable = false)
	@ManyToOne
	private EOCustBusinessApp custBusinessApp;

	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getReprogramId() {
		return reprogramId;
	}

	public void setReprogramId(Long reprogramId) {
		this.reprogramId = reprogramId;
	}

	public Date getReprogramDate() {
		return reprogramDate;
	}

	public void setReprogramDate(Date reprogramDate) {
		this.reprogramDate = reprogramDate;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<EOReProgramItem> getReprograms() {
		return reprograms;
	}

	public void setReprograms(List<EOReProgramItem> reprograms) {
		this.reprograms = reprograms;
	}

}
