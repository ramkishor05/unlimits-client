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
@Table(name = "EOCLIENT_UNLIMITS_EXAMPLE_ITEM")
public class EOUnlimitsExampleItem extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "IMAGE_ID", nullable = true)
	private String imageId;

	@Column(name = "IMAGE_URL", nullable = true)
	private String imageUrl;

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

	@JoinColumn(name = "UNLIMITS_EXAMPLE_ID", referencedColumnName = "ID")
	@ManyToOne
	private EOUnlimitsExample unlimitsExample;

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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public EOUnlimitsExample getUnlimitsExample() {
		return unlimitsExample;
	}

	public void setUnlimitsExample(EOUnlimitsExample unlimitsExample) {
		this.unlimitsExample = unlimitsExample;
	}

}
