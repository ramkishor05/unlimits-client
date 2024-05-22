package com.brijframework.client.entities.visualise;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOCustObject;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EOClientVisualise extends EOCustObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name ="CATEGORY_ID")
	private Long categoryId;
	
	@Column(name ="SUB_CATEGORY_ID")
	private Long subCategoryId;

	@JoinColumn(name = CUST_BUSINESS_APP_ID, nullable = false)
	@ManyToOne
	private EOCustBusinessApp custBusinessApp;

	public EOCustBusinessApp getCustBusinessApp() {
		return custBusinessApp;
	}

	public void setCustBusinessApp(EOCustBusinessApp custBusinessApp) {
		this.custBusinessApp = custBusinessApp;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
}
