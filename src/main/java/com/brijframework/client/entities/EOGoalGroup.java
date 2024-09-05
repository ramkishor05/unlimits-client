/**
 * 
 */
package com.brijframework.client.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_GOAL_GROUP")
public class EOGoalGroup extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "GOAL_DATE")
	@Temporal(TemporalType.DATE)
	private Date goalDate;
	
	@Column(name ="GOAL_NAME")
	private String goalName;
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private List<EOGoalItem> goals;

	public Date getGoalDate() {
		return goalDate;
	}

	public void setGoalDate(Date goalDate) {
		this.goalDate = goalDate;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public List<EOGoalItem> getGoals() {
		if(goals==null) {
			goals=new ArrayList<EOGoalItem>();
		}
		return goals;
	}

	public void setGoals(List<EOGoalItem> goals) {
		this.goals = goals;
	}

}
