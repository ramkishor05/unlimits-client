package com.brijframework.client.device.model;

import java.util.List;

public class UIDeviceJournalGroup extends UIDeviceModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String journalDate;
	private List<UIDeviceJournalItem> journals;
	
	public UIDeviceJournalGroup() {
	}
	

	public UIDeviceJournalGroup(String journalDate, List<UIDeviceJournalItem> journals) {
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

	public List<UIDeviceJournalItem> getJournals() {
		return journals;
	}

	public void setJournals(List<UIDeviceJournalItem> journals) {
		this.journals = journals;
	}

}
