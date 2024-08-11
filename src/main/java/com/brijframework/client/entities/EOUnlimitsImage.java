/**
 * 
 */
package com.brijframework.client.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_IMAGE")
public class EOUnlimitsImage extends EOUnlimits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "unlimitsImage", cascade = CascadeType.ALL)
	private List<EOUnlimitsImageItem> imageItems;

	public List<EOUnlimitsImageItem> getImageItems() {
		if(imageItems==null) {
			imageItems=new ArrayList<EOUnlimitsImageItem>();
		}
		return imageItems;
	}

	public void setImageItems(List<EOUnlimitsImageItem> imageItems) {
		this.imageItems = imageItems;
	}

}
