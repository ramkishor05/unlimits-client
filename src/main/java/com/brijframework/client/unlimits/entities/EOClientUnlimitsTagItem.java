/**
 * 
 */
package com.brijframework.client.unlimits.entities;

import com.brijframework.client.entities.EOCustObject;

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
public class EOClientUnlimitsTagItem extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TAG_ID", nullable = true)
	private String tagId;
	
	@Column(name = "TAG_NAME", nullable = true)
	private String tagName;
	
	@JoinColumn(name = "UNLIMITS_TAG_ID", referencedColumnName = "ID")
	@ManyToOne
	private EOClientUnlimitsTag unlimitsTag;

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

	public EOClientUnlimitsTag getUnlimitsTag() {
		return unlimitsTag;
	}

	public void setUnlimitsTag(EOClientUnlimitsTag UnlimitsTag) {
		this.unlimitsTag = UnlimitsTag;
	}
	
}
