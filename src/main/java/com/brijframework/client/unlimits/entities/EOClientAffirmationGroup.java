package com.brijframework.client.unlimits.entities;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import java.util.List;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOEntityObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOCLIENT_AFFIRMATION_ITEM")
public class EOClientAffirmationGroup extends EOEntityObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name ="NAME")
	private String name;
	
	@Column(name ="DESCRIPTION")
	private String description;

	@Column(name = "AFFIRMATION_ID")
	private Long affirmationId;

	@Column(name = "RESOURCE_ID")
	private Long resourceId;

	@Column(name ="URL")
	private String url;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOClientAffirmationItem> affirmations;

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

	public Long getAffirmationId() {
		return affirmationId;
	}

	public void setAffirmationId(Long affirmationId) {
		this.affirmationId = affirmationId;
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

	public List<EOClientAffirmationItem> getAffirmations() {
		return affirmations;
	}

	public void setAffirmations(List<EOClientAffirmationItem> affirmations) {
		this.affirmations = affirmations;
	}
	
}
