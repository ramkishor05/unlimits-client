/**
 * 
 */
package com.brijframework.client.unlimits.entities;

import static com.brijframework.client.constants.TableConstants.CUST_BUSINESS_APP_ID;

import java.util.Date;

import com.brijframework.client.entities.EOCustBusinessApp;
import com.brijframework.client.entities.EOCustObject;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author omnie
 */
@MappedSuperclass
public abstract class EOClientUnlimits extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_AT")
	private Date date;

	@Column(name = "NAME")
	private String name;

	@Column(name = "YEAR")
	private Long year;

	@Column(name = "CATEGORY_ID")
	private Long categoryId;

	@Column(name = "SUB_CATEGORY_ID")
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
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
