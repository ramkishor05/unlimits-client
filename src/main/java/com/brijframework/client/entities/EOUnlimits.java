/**
 * 
 */
package com.brijframework.client.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_UNLIMITS")
public class EOUnlimits extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_AT")
	@CreationTimestamp
	private Date date;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TYPE")
    private String type;
	
	@OneToMany(mappedBy = "unlimits")
	private List<EOUnlimitsItem> items;
	
	@OneToMany(mappedBy = "unlimits")
	private List<EOUnlimitsVisualize> visualizes;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<EOUnlimitsItem> getItems() {
		return items;
	}

	public void setItems(List<EOUnlimitsItem> items) {
		this.items = items;
	}

	public List<EOUnlimitsVisualize> getVisualizes() {
		return visualizes;
	}

	public void setVisualizes(List<EOUnlimitsVisualize> visualizes) {
		this.visualizes = visualizes;
	}
	
}
