package com.brijframework.client.unlimits.model;

import java.util.List;

public class UIClientJournalGroup {

	private String journalDate;
	private List<UIClientJournal> journals;
	
	public UIClientJournalGroup() {
	}
	

	public UIClientJournalGroup(String journalDate, List<UIClientJournal> journals) {
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

	public List<UIClientJournal> getJournals() {
		return journals;
	}

	public void setJournals(List<UIClientJournal> journals) {
		this.journals = journals;
	}

}
