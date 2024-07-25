/**
 * 
 */
package com.brijframework.client.unlimits.entities;

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
public class EOCustUnlimitsImage extends EOClientUnlimits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "unlimitsImage", cascade = CascadeType.ALL)
	private List<EOClientUnlimitsImageItem> imageItems;

	public List<EOClientUnlimitsImageItem> getImageItems() {
		if(imageItems==null) {
			imageItems=new ArrayList<EOClientUnlimitsImageItem>();
		}
		return imageItems;
	}

	public void setImageItems(List<EOClientUnlimitsImageItem> imageItems) {
		this.imageItems = imageItems;
	}

}
