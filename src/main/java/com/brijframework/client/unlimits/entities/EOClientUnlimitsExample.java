/**
 * 
 */
package com.brijframework.client.unlimits.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *  @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_EXAMPLE")
public class EOClientUnlimitsExample extends EOClientUnlimits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long exampleId;

	@OneToMany(mappedBy = "unlimitsExample", cascade = CascadeType.ALL)
	private List<EOClientUnlimitsExampleItem> exampleItems;

	public Long getExampleId() {
		return exampleId;
	}

	public void setExampleId(Long exampleId) {
		this.exampleId = exampleId;
	}

	public List<EOClientUnlimitsExampleItem> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<EOClientUnlimitsExampleItem> exampleItems) {
		this.exampleItems = exampleItems;
	}
}
