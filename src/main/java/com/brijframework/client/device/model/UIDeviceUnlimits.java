package com.brijframework.client.device.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.constants.UnlimitsType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIDeviceUnlimits extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String date;

	private Date unlimitsDate;

	private String name;

	private UnlimitsType type;

	private List<UIDeviceUnlimitsItem> items;

	private Map<Integer, UIDeviceUnlimitsVisualize> visualizeMap;

	public List<UIDeviceUnlimitsItem> getItems() {
		return items;
	}

	public void setItems(List<UIDeviceUnlimitsItem> items) {
		this.items = items;
	}

	public Map<Integer, UIDeviceUnlimitsVisualize> getVisualizeMap() {
		return visualizeMap;
	}

	public void setVisualizeMap(Map<Integer, UIDeviceUnlimitsVisualize> visualizeMap) {
		this.visualizeMap = visualizeMap;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UnlimitsType getType() {
		if (type == null) {
			type = UnlimitsType.WORDS;
		}
		return type;
	}

	public void setType(UnlimitsType type) {
		this.type = type;
	}

	public Date getUnlimitsDate() {
		if (unlimitsDate == null) {
			unlimitsDate = new Date();
		}
		return unlimitsDate;
	}

	public void setUnlimitsDate(Date unlimitsDate) {
		this.unlimitsDate = unlimitsDate;
	}

}
