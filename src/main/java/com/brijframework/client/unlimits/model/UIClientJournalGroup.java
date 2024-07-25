package com.brijframework.client.unlimits.model;

import java.util.List;

public class UIClientJournalGroup {

	private String journalDate;
	private List<UICustJournalItem> journals;
	
	public UIClientJournalGroup() {
	}
	

	public UIClientJournalGroup(String journalDate, List<UICustJournalItem> journals) {
		super();
		this.journalDate = journalDate;
		this.journals = journals;
	}

	public String getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(String journalDate) {
		this.journalDate = journalDate;
	}

	public List<UICustJournalItem> getJournals() {
		return journals;
	}

	public void setJournals(List<UICustJournalItem> journals) {
		this.journals = journals;
	}

}
