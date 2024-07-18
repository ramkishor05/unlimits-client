package com.brijframework.client.unlimits.model;

import java.util.List;

public class UIClientJournalGroup {

	private String journalDate;
	private List<UIClientJournalItem> journals;
	
	public UIClientJournalGroup() {
	}
	

	public UIClientJournalGroup(String journalDate, List<UIClientJournalItem> journals) {
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

	public List<UIClientJournalItem> getJournals() {
		return journals;
	}

	public void setJournals(List<UIClientJournalItem> journals) {
		this.journals = journals;
	}

}
