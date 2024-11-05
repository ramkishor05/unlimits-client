package com.brijframework.client.global.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UIGlobalDashboardCategoryWiseUnlimits {
	
	private Long mainCategoryId;
	
	private String mainCategoryName;
	
	private Long subCategoryId;
	
	private String subCategoryName;
	
	private String color;
	
	private Long unlimitsCount;
	
	public UIGlobalDashboardCategoryWiseUnlimits() {
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

	public Long getUnlimitsCount() {
		return unlimitsCount;
	}

	public void setUnlimitsCount(Long unlimitsCount) {
		this.unlimitsCount = unlimitsCount;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void addCount(Long unlimitsCount) {
		if(this.unlimitsCount==null) {
			this.unlimitsCount=0l;
		}
		this.unlimitsCount+=unlimitsCount;
	}

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
}
