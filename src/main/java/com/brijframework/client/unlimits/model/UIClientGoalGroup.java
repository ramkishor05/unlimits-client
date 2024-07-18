package com.brijframework.client.unlimits.model;

import java.util.List;

import org.unlimits.rest.model.UIModel;

public class UIClientGoalGroup extends UIModel {

	private String goalName;
	private List<UIClientGoalItem> goals;

	public UIClientGoalGroup() {
	}

	public UIClientGoalGroup(String goalName, List<UIClientGoalItem> goals) {
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

	public List<UIClientGoalItem> getGoals() {
		return goals;
	}

	public void setGoals(List<UIClientGoalItem> goals) {
		this.goals = goals;
	}

}
