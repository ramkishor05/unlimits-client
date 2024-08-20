package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

public class UIDeviceVisualizeResponse extends UIModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String visualizeDate;
	private String visualizeRequest;
	private String visualizeResponse;

	public String getVisualizeDate() {
		return visualizeDate;
	}

	public void setVisualizeDate(String visualizeDate) {
		this.visualizeDate = visualizeDate;
	}

	public String getVisualizeRequest() {
		return visualizeRequest;
	}

	public void setVisualizeRequest(String visualizeRequest) {
		this.visualizeRequest = visualizeRequest;
	}

	public String getVisualizeResponse() {
		return visualizeResponse;
	}

	public void setVisualizeResponse(String visualizeResponse) {
		this.visualizeResponse = visualizeResponse;
	}

}
