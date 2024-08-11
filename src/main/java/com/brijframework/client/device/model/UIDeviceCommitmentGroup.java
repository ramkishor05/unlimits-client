package com.brijframework.client.device.model;

import java.util.List;

public class UIDeviceCommitmentGroup extends UIDeviceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commitmentName;
	private List<UIDeviceCommitmentItem> commitments;

	public UIDeviceCommitmentGroup() {
	}

	public UIDeviceCommitmentGroup(String commitmentName, List<UIDeviceCommitmentItem> commitments) {
		super();
		this.commitmentName = commitmentName;
		this.commitments = commitments;
	}

	public String getCommitmentName() {
		return commitmentName;
	}

	public void setCommitmentName(String commitmentName) {
		this.commitmentName = commitmentName;
	}

	public List<UIDeviceCommitmentItem> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<UIDeviceCommitmentItem> commitments) {
		this.commitments = commitments;
	}

}
