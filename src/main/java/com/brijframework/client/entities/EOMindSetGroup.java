package com.brijframework.client.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EOCLIENT_MINDSET_ITEM")
public class EOMindSetGroup extends EOCustObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name ="NAME")
	private String name;
	
	@Column(name ="TITLE")
	private String title;
	
	@Column(name ="DESCRIPTION")
	private String description;
	
	@Column(name = "MINDSET_DATE")
	private Date mindsetDate;

	@Column(name = "MINDSET_ID")
	private Long mindsetId;

	@Column(name = "RESOURCE_ID")
	private Long resourceId;

	@Column(name ="URL")
	private String url;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOMindSetItem> mindSets;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getMindsetId() {
		return mindsetId;
	}

	public void setMindsetId(Long mindsetId) {
		this.mindsetId = mindsetId;
	}

	public Date getMindsetDate() {
		return mindsetDate;
	}

	public void setMindsetDate(Date mindsetDate) {
		this.mindsetDate = mindsetDate;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<EOMindSetItem> getMindSets() {
		if(mindSets==null) {
			mindSets=new ArrayList<EOMindSetItem>();
		}
		return mindSets;
	}

	public void setMindSets(List<EOMindSetItem> mindSets) {
		this.mindSets = mindSets;
	}
	
}
