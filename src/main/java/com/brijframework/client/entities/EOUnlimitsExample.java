/**
 * 
 */
package com.brijframework.client.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *  @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS_EXAMPLE")
public class EOUnlimitsExample extends EOUnlimits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "EXAMPLE_ID", nullable = true)
	private Long exampleId;
	
	@Column(name = "EXAMPLE_DATE", nullable = true)
	private Long exampleDate;
	
	@Column(name = "PROFILE_NAME", nullable = true)
	private String profileName;
	
	@Column(name = "PROFILE_PICTURE_URL", nullable = true)
	private String profilePictureURL;
	
	@Column(name = "PROFILE_AGE", nullable = true)
	private String profileAge;
	
	@Column(name = "PROFILE_POSITION", nullable = true)
	private String profilePosition;
	
	@Column(name = "PROFILE_ORGANIZATION", nullable = true)
	private String profileOrganization;
	
	@Column(name = "POSTER_URL", nullable = true)
	private String posterUrl;

	@OneToMany(mappedBy = "unlimitsExample", cascade = CascadeType.ALL)
	private List<EOUnlimitsExampleItem> exampleItems;

	public Long getExampleId() {
		return exampleId;
	}

	public void setExampleId(Long exampleId) {
		this.exampleId = exampleId;
	}

	public Long getExampleDate() {
		return exampleDate;
	}

	public void setExampleDate(Long exampleDate) {
		this.exampleDate = exampleDate;
	}

	public List<EOUnlimitsExampleItem> getExampleItems() {
		return exampleItems;
	}

	public void setExampleItems(List<EOUnlimitsExampleItem> exampleItems) {
		this.exampleItems = exampleItems;
	}
}
