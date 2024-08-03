package com.brijframework.client.unlimits.model;

import java.text.SimpleDateFormat;

import static com.brijframework.client.constants.ClientConstants.UI_DATE_FORMAT_MMMM_DD_YYYY;
import java.util.Date;

import org.unlimits.rest.model.UIModel;

public class UICustJournalItem extends UIModel{

	private Long journalId;
	
	private String journalDate;
	
	private String journalQuestion;

	private String journalAnswer;

	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

	public String getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(String journalDate) {
		this.journalDate = journalDate;
	}

	public String getJournalQuestion() {
		return journalQuestion;
	}

	public void setJournalQuestion(String journalQuestion) {
		this.journalQuestion = journalQuestion;
	}

	public String getJournalAnswer() {
		return journalAnswer;
	}

	public void setJournalAnswer(String journalAnswer) {
		this.journalAnswer = journalAnswer;
	}

	public Date toJournalDate() {
		try {
			return new SimpleDateFormat(UI_DATE_FORMAT_MMMM_DD_YYYY).parse(journalDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}
}
