package com.brijframework.client.device.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIDeviceGoalGroup extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goalName;
	private List<UIDeviceGoalItem> goals;

	public UIDeviceGoalGroup() {
	}

	public UIDeviceGoalGroup(String goalName, List<UIDeviceGoalItem> goals) {
		super();
		this.goalName = goalName;
		this.goals = goals;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public List<UIDeviceGoalItem> getGoals() {
		return goals;
	}

	public void setGoals(List<UIDeviceGoalItem> goals) {
		this.goals = goals;
	}

}
