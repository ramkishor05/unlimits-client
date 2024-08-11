package com.brijframework.client.global.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalCommitmentGroup extends UIGlobalModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commitmentName;
	private List<UIGlobalCommitmentItem> commitments;

	public UIGlobalCommitmentGroup() {
	}

	public UIGlobalCommitmentGroup(String commitmentName, List<UIGlobalCommitmentItem> commitments) {
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

	public List<UIGlobalCommitmentItem> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<UIGlobalCommitmentItem> commitments) {
		this.commitments = commitments;
	}

}
