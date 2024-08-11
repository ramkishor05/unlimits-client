package com.brijframework.client.global.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalGoalGroup extends UIGlobalModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goalName;
	private List<UIGlobalGoalItem> goals;

	public UIGlobalGoalGroup() {
	}

	public UIGlobalGoalGroup(String goalName, List<UIGlobalGoalItem> goals) {
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

	public List<UIGlobalGoalItem> getGoals() {
		return goals;
	}

	public void setGoals(List<UIGlobalGoalItem> goals) {
		this.goals = goals;
	}

}
