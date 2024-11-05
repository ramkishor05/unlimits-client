package com.brijframework.client.global.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalDashboard {

	private long totalUnlimits;

	private long totalMindSets;

	private long totalJournals;

	private long totalAffirmations;

	private long totalReprograms;

	private long totalGoals;
	
	private Collection<UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsBySubCategory;
	
	private Collection<UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsByMainCategory;

	public long getTotalUnlimits() {
		return totalUnlimits;
	}

	public void setTotalUnlimits(long totalUnlimits) {
		this.totalUnlimits = totalUnlimits;
	}

	public long getTotalMindSets() {
		return totalMindSets;
	}

	public void setTotalMindSets(long totalMindSets) {
		this.totalMindSets = totalMindSets;
	}

	public long getTotalJournals() {
		return totalJournals;
	}

	public void setTotalJournals(long totalJournals) {
		this.totalJournals = totalJournals;
	}

	public long getTotalAffirmations() {
		return totalAffirmations;
	}

	public void setTotalAffirmations(long totalAffirmations) {
		this.totalAffirmations = totalAffirmations;
	}

	public long getTotalReprograms() {
		return totalReprograms;
	}

	public void setTotalReprograms(long totalReprograms) {
		this.totalReprograms = totalReprograms;
	}

	public long getTotalGoals() {
		return totalGoals;
	}

	public void setTotalGoals(long totalGoals) {
		this.totalGoals = totalGoals;
	}

	public Collection<UIGlobalDashboardCategoryWiseUnlimits> getTotalUnlimitsBySubCategory() {
		return totalUnlimitsBySubCategory;
	}

	public void setTotalUnlimitsBySubCategory(Collection<UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsBySubCategory) {
		this.totalUnlimitsBySubCategory = totalUnlimitsBySubCategory;
	}

	public Collection<UIGlobalDashboardCategoryWiseUnlimits> getTotalUnlimitsByMainCategory() {
		return totalUnlimitsByMainCategory;
	}

	public void setTotalUnlimitsByMainCategory(Collection<UIGlobalDashboardCategoryWiseUnlimits> totalUnlimitsByMainCategory) {
		this.totalUnlimitsByMainCategory = totalUnlimitsByMainCategory;
	}

}
