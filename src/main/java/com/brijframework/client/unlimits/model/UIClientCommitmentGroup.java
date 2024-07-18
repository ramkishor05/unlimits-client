package com.brijframework.client.unlimits.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIClientCommitmentGroup extends UIModel {

	private String commitmentName;
	private List<UIClientCommitmentItem> commitments;

	public UIClientCommitmentGroup() {
	}

	public UIClientCommitmentGroup(String commitmentName, List<UIClientCommitmentItem> commitments) {
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

	public List<UIClientCommitmentItem> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<UIClientCommitmentItem> commitments) {
		this.commitments = commitments;
	}

}
