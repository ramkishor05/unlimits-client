package com.brijframework.client.unlimits.model;

import org.unlimits.rest.model.UIModel;

public class UIClientCommitmentItem extends UIModel{

	private String title;

	private String status;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
