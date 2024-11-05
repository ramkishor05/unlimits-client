package com.brijframework.client.entities;

import com.brijframework.client.constants.UnlimitsType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "EOCLIENT_UNLIMITS_VISUALIZE", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"VISUALIZE_YEAR", "UNLIMITS_ID" })
})
public class EOUnlimitsVisualize extends EOEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "VISUALIZE_DATE")
	private String visualizeDate;

	@Column(name = "VISUALIZE_REQUEST", columnDefinition = "LONGBLOB")
	private String visualizeRequest;

	@Column(name = "VISUALIZE_RESPONSE", columnDefinition = "LONGBLOB")
	private String visualizeResponse;

	@Column(name = "VISUALIZE_YEAR")
	private Integer visualizeYear;
	
	@Column(name = "SUBCATEGORY_ID")
	private Long subCategoryId;
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "UNLIMITS_ID" , referencedColumnName = "id")
	private EOUnlimits unlimits;
	
	@Column(name = "VISUALIZE_TYPE")
	private String type;

	public String getVisualizeDate() {
		return visualizeDate;
	}

	public void setVisualizeDate(String visualizeDate) {
		this.visualizeDate = visualizeDate;
	}

	public String getVisualizeRequest() {
		return visualizeRequest;
	}

	public void setVisualizeRequest(String visualizeRequest) {
		this.visualizeRequest = visualizeRequest;
	}

	public String getVisualizeResponse() {
		return visualizeResponse;
	}

	public void setVisualizeResponse(String visualizeResponse) {
		this.visualizeResponse = visualizeResponse;
	}

	public Integer getVisualizeYear() {
		return visualizeYear;
	}

	public void setVisualizeYear(Integer visualizeYear) {
		this.visualizeYear = visualizeYear;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public EOUnlimits getUnlimits() {
		return unlimits;
	}

	public void setUnlimits(EOUnlimits unlimits) {
		this.unlimits = unlimits;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UnlimitsType getUnlimitsType() {
		return UnlimitsType.findByType(type);
	}

}
