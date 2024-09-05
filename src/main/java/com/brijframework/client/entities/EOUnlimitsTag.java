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
 *  @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_TAG")
public class EOUnlimitsTag extends EOUnlimits{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "unlimitsTag", cascade = CascadeType.ALL)
	private List<EOUnlimitsTagItem> tagItems;
	
	@OneToMany(mappedBy = "unlimitsTag", cascade = CascadeType.ALL)
	private List<EOUnlimitsVisualize> unlimitsVisualizeList;

	public List<EOUnlimitsTagItem> getTagItems() {
		if(tagItems==null) {
			tagItems=new ArrayList<EOUnlimitsTagItem>();
		}
		return tagItems;
	}

	public void setTagItems(List<EOUnlimitsTagItem> tagItems) {
		this.tagItems = tagItems;
	}
	

	public List<EOUnlimitsVisualize> getUnlimitsVisualizeList() {
		return unlimitsVisualizeList;
	}

	public void setUnlimitsVisualizeList(List<EOUnlimitsVisualize> unlimitsVisualizeList) {
		this.unlimitsVisualizeList = unlimitsVisualizeList;
	}

}
