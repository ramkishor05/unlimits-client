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
 *  @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_TAG_ITEM")
public class EOUnlimitsTagItem extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column(name = "YEAR")
	private Long year;
	
	@Column(name = "TAG_ID", nullable = true)
	private String tagId;
	
	@Column(name = "TAG_NAME", nullable = true)
	private String tagName;
	
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

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public EOUnlimitsTag getUnlimitsTag() {
		return unlimitsTag;
	}

	public void setUnlimitsTag(EOUnlimitsTag UnlimitsTag) {
		this.unlimitsTag = UnlimitsTag;
	}
	
}
