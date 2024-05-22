package com.brijframework.client.entities;
import static com.brijframework.client.constants.TableConstants.*;

import com.brijframework.client.entities.visualise.EOClientVisualiseExample;
import com.brijframework.client.entities.visualise.EOClientVisualiseImage;
import com.brijframework.client.entities.visualise.EOClientVisualiseTag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = EOCUST_BUSINESS_APP, uniqueConstraints = {@UniqueConstraint (columnNames = {APP_ID, CUST_ID, BUSINESS_ID })})
public class EOCustBusinessApp extends EOCustObject {

	private static final long serialVersionUID = 1L;
	
	@Column(name = APP_ID, nullable = false)
	private long appId;

	@Column(name = CUST_ID, nullable = false)
	private long custId;

	@Column(name = BUSINESS_ID, nullable = false)
	private long businessId;
	
	@JoinColumn(name = "CURRENT_VISUALISE_TAG_ID")
	@OneToOne
	private EOClientVisualiseTag clientVisualiseTag;
	
	@JoinColumn(name = "CURRENT_VISUALISE_IMAGE_ID")
	@OneToOne
	private EOClientVisualiseImage clientVisualiseImage;
	
	@JoinColumn(name = "CURRENT_VISUALISE_EXAMPLE_ID")
	@OneToOne
	private EOClientVisualiseExample clientVisualiseExample;
	
	public EOCustBusinessApp() {
	}

	public EOCustBusinessApp(long appId, long custId, long businessId) {
		super();
		this.appId = appId;
		this.custId = custId;
		this.businessId = businessId;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	public EOClientVisualiseTag getClientVisualiseTag() {
		return clientVisualiseTag;
	}

	public void setClientVisualiseTag(EOClientVisualiseTag clientVisualiseTag) {
		this.clientVisualiseTag = clientVisualiseTag;
	}

	public EOClientVisualiseImage getClientVisualiseImage() {
		return clientVisualiseImage;
	}

	public void setClientVisualiseImage(EOClientVisualiseImage clientVisualiseImage) {
		this.clientVisualiseImage = clientVisualiseImage;
	}

	public EOClientVisualiseExample getClientVisualiseExample() {
		return clientVisualiseExample;
	}

	public void setClientVisualiseExample(EOClientVisualiseExample clientVisualiseExample) {
		this.clientVisualiseExample = clientVisualiseExample;
	}
	
}
