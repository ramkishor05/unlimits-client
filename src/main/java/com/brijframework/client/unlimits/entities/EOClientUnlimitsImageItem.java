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
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_IMAGE_ITEM")
public class EOClientUnlimitsImageItem extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column(name = "YEAR")
	private Long year;

	@Column(name = "IMAGE_ID", nullable = true)
	private String imageId;

	@Column(name = "IMAGE_URL", nullable = true)
	private String imageUrl;

	@JoinColumn(name = "UNLIMITS_IMAGE_ID", referencedColumnName = "ID")
	@ManyToOne
	private EOCustUnlimitsImage unlimitsImage;

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

	public EOCustUnlimitsImage getUnlimitsImage() {
		return unlimitsImage;
	}

	public void setUnlimitsImage(EOCustUnlimitsImage unlimitsImage) {
		this.unlimitsImage = unlimitsImage;
	}

}
