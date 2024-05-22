/**
 * 
 */
package com.brijframework.client.entities.visualise;

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
@Table(name = "EOCLIENT_VISUALISE_IMAGE")
public class EOClientVisualiseImage extends EOClientVisualise {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "visualiseImage", cascade = CascadeType.ALL)
	private List<EOClientVisualiseImageItem> imageItems;

	public List<EOClientVisualiseImageItem> getImageItems() {
		if(imageItems==null) {
			imageItems=new ArrayList<EOClientVisualiseImageItem>();
		}
		return imageItems;
	}

	public void setImageItems(List<EOClientVisualiseImageItem> imageItems) {
		this.imageItems = imageItems;
	}

}
