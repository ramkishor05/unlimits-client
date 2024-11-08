package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.entities.EOUnlimits;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceUnlimitsItem extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long year;

	private Long tagId;

	private String tagName;

	private Long imageId;

	private String imageUrl;

	private Long mainCategoryId;

	private String mainCategoryName;

	private Long subCategoryId;

	private String subCategoryName;
	
	private Long unlimitsId;

	@JsonIgnore
	private EOUnlimits unlimits;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
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

	public Long getUnlimitsId() {
		return unlimitsId;
	}

	public void setUnlimitsId(Long unlimitsId) {
		this.unlimitsId = unlimitsId;
	}

	public void setUnlimits(EOUnlimits unlimits) {
		this.unlimits=unlimits;
	}
	
	public EOUnlimits getUnlimits() {
		return unlimits;
	}
}
