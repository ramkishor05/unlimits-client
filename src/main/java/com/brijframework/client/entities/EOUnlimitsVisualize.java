package com.brijframework.client.entities;

import com.brijframework.client.constants.UnlimitsType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "EOCLIENT_UNLIMITS_VISUALIZE", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"VISUALIZE_YEAR", "UNLIMITS_IMAGE_ID" }),
		@UniqueConstraint(columnNames = {"VISUALIZE_YEAR", "UNLIMITS_EXAMPLE_ID" }),
		@UniqueConstraint(columnNames = {"VISUALIZE_YEAR", "UNLIMITS_TAG_ID" })
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
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "UNLIMITS_IMAGE_ID" , referencedColumnName = "id")
	private EOUnlimitsImage unlimitsImage;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNLIMITS_EXAMPLE_ID" , referencedColumnName = "id")
	private EOUnlimitsExample unlimitsExample;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UNLIMITS_TAG_ID", referencedColumnName = "id")
	private EOUnlimitsTag unlimitsTag;
	
	@Column(name = "VISUALIZE_TYPE")
	@Enumerated(EnumType.STRING)
	private UnlimitsType type;

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

	public UnlimitsType getType() {
		return type;
	}

	public void setType(UnlimitsType type) {
		this.type = type;
	}


}
