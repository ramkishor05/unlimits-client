/**
 * 
 */
package com.brijframework.client.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_TAG_ITEM")
public class EOUnlimitsTagItem extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "YEAR")
	private Integer year;

	@Column(name = "TAG_ID", nullable = true)
	private String tagId;

	@Column(name = "TAG_NAME", nullable = true)
	private String tagName;

	@Column(name = "MAIN_CATEGORY_ID")
	private Long mainCategoryId;

	@Column(name = "MAIN_CATEGORY_NAME")
	private String mainCategoryName;

	@Column(name = "SUB_CATEGORY_ID")
	private Long subCategoryId;

	@Column(name = "SUB_CATEGORY_NAME")
	private String subCategoryName;

	@JoinColumn(name = "UNLIMITS_TAG_ID", referencedColumnName = "ID")
	@ManyToOne
	private EOUnlimitsTag unlimitsTag;

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public String getMainCategoryName() {
		return mainCategoryName;
	}

	public void setMainCategoryName(String mainCategoryName) {
		this.mainCategoryName = mainCategoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public EOUnlimitsTag getUnlimitsTag() {
		return unlimitsTag;
	}

	public void setUnlimitsTag(EOUnlimitsTag UnlimitsTag) {
		this.unlimitsTag = UnlimitsTag;
	}

}
