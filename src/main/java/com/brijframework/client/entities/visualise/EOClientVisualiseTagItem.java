/**
 * 
 */
package com.brijframework.client.entities.visualise;

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
@Table(name = "EOCLIENT_VISUALISE_TAG_ITEM")
public class EOClientVisualiseTagItem extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TAG_ID", nullable = true)
	private Long tagId;
	
	@Column(name = "TAG_NAME", nullable = true)
	private String tagName;
	
	@JoinColumn(name = "VISUALISE_TAG_ID", referencedColumnName = "ID")
	@ManyToOne
	private EOClientVisualiseTag visualiseTag;

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

	public EOClientVisualiseTag getVisualiseTag() {
		return visualiseTag;
	}

	public void setVisualiseTag(EOClientVisualiseTag visualiseTag) {
		this.visualiseTag = visualiseTag;
	}
	
}
