package com.brijframework.client.entities;
import static com.brijframework.client.constants.TableConstants.APP_ID;
import static com.brijframework.client.constants.TableConstants.BUSINESS_ID;
import static com.brijframework.client.constants.TableConstants.CUST_ID;
import static com.brijframework.client.constants.TableConstants.EOCUST_BUSINESS_APP;

import org.unlimits.rest.crud.beans.IUserAccount;

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
public class EOCustBusinessApp extends EOEntityObject implements IUserAccount{

	private static final long serialVersionUID = 1L;
	
	@Column(name = APP_ID, nullable = false)
	private long appId;

	@Column(name = CUST_ID, nullable = false)
	private long custId;

	@Column(name = BUSINESS_ID, nullable = false)
	private long businessId;
	
	@JoinColumn(name = "CURRENT_UNLIMITS_TAG_ID")
	@OneToOne
	private EOUnlimitsTag unlimitsTag;
	
	@JoinColumn(name = "CURRENT_UNLIMITS_IMAGE_ID")
	@OneToOne
	private EOUnlimitsImage unlimitsImage;
	
	@JoinColumn(name = "CURRENT_UNLIMITS_EXAMPLE_ID")
	@OneToOne
	private EOUnlimitsExample unlimitsExample;
	
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

	public EOUnlimitsTag getUnlimitsTag() {
		return unlimitsTag;
	}

	public void setUnlimitsTag(EOUnlimitsTag clientUnlimitsTag) {
		this.unlimitsTag = clientUnlimitsTag;
	}

	public EOUnlimitsImage getUnlimitsImage() {
		return unlimitsImage;
	}

	public void setUnlimitsImage(EOUnlimitsImage clientUnlimitsImage) {
		this.unlimitsImage = clientUnlimitsImage;
	}

	public EOUnlimitsExample getUnlimitsExample() {
		return unlimitsExample;
	}

	public void setUnlimitsExample(EOUnlimitsExample clientUnlimitsExample) {
		this.unlimitsExample = clientUnlimitsExample;
	}
	
}
