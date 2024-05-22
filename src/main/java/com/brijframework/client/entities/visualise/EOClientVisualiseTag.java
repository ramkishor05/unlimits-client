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
 *  @author omnie
 */
@Entity
@Table(name = "EOCLIENT_VISUALISE_TAG")
public class EOClientVisualiseTag extends EOClientVisualise{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "visualiseTag", cascade = CascadeType.ALL)
	private List<EOClientVisualiseTagItem> tagItems;

	public List<EOClientVisualiseTagItem> getTagItems() {
		if(tagItems==null) {
			tagItems=new ArrayList<EOClientVisualiseTagItem>();
		}
		return tagItems;
	}

	public void setTagItems(List<EOClientVisualiseTagItem> tagItems) {
		this.tagItems = tagItems;
	}
	
}
