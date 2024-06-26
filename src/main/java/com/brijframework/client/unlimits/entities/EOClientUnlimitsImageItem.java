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

	@Column(name = "IMAGE_ID", nullable = true)
	private Long imageId;

	@Column(name = "IMAGE_NAME", nullable = true)
	private String imageName;

	@Column(name = "IMAGE_URL", nullable = true)
	private String imageUrl;

	@Column(name = "IMAGE_CONTENT", nullable = true, columnDefinition = "LONGTEXT")
	private String imageContent;

	@JoinColumn(name = "UNLIMITS_IMAGE_ID", referencedColumnName = "ID")
	@ManyToOne
	private EOClientUnlimitsImage unlimitsImage;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageContent() {
		return imageContent;
	}

	public void setImageContent(String imageContent) {
		this.imageContent = imageContent;
	}

	public EOClientUnlimitsImage getUnlimitsImage() {
		return unlimitsImage;
	}

	public void setUnlimitsImage(EOClientUnlimitsImage unlimitsImage) {
		this.unlimitsImage = unlimitsImage;
	}

}
