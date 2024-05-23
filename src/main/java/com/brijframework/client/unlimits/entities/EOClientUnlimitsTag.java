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
 *  @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_TAG")
public class EOClientUnlimitsTag extends EOClientUnlimits{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "unlimitsTag", cascade = CascadeType.ALL)
	private List<EOClientUnlimitsTagItem> tagItems;

	public List<EOClientUnlimitsTagItem> getTagItems() {
		if(tagItems==null) {
			tagItems=new ArrayList<EOClientUnlimitsTagItem>();
		}
		return tagItems;
	}

	public void setTagItems(List<EOClientUnlimitsTagItem> tagItems) {
		this.tagItems = tagItems;
	}
	
}
