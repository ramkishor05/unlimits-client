package com.brijframework.client.global.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UIGlobalJournalGroup extends UIGlobalModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String journalDate;
	private List<UIGlobalJournalItem> journals;
	
	public UIGlobalJournalGroup() {
	}
	

	public UIGlobalJournalGroup(String journalDate, List<UIGlobalJournalItem> journals) {
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

	public List<UIGlobalJournalItem> getJournals() {
		return journals;
	}

	public void setJournals(List<UIGlobalJournalItem> journals) {
		this.journals = journals;
	}

}
