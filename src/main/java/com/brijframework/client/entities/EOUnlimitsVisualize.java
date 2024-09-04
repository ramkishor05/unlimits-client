package com.brijframework.client.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOCLIENT_UNLIMITS_VISUALIZE")
public class EOUnlimitsVisualize extends EOCustObject {

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
	
	@ManyToOne
	@JoinColumn(name = "UNLIMITS_IMAGE_ID")
	private EOUnlimitsImage unlimitsImage;
	
	@ManyToOne
	@JoinColumn(name = "UNLIMITS_EXAMPLE_ID")
	private EOUnlimitsExample unlimitsExample;
	
	@ManyToOne
	@JoinColumn(name = "UNLIMITS_TAG_ID")
	private EOUnlimitsTag unlimitsTag;
	
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

	public EOUnlimitsImage getUnlimitsImage() {
		return unlimitsImage;
	}

	public void setUnlimitsImage(EOUnlimitsImage unlimitsImage) {
		this.unlimitsImage = unlimitsImage;
	}

	public EOUnlimitsExample getUnlimitsExample() {
		return unlimitsExample;
	}

	public void setUnlimitsExample(EOUnlimitsExample unlimitsExample) {
		this.unlimitsExample = unlimitsExample;
	}

	public EOUnlimitsTag getUnlimitsTag() {
		return unlimitsTag;
	}

	public void setUnlimitsTag(EOUnlimitsTag unlimitsTag) {
		this.unlimitsTag = unlimitsTag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
