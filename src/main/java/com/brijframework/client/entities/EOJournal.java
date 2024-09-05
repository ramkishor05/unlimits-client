/**
 * 
 */
package com.brijframework.client.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author omnie
 */
@Entity
@Table(name = "EOCLIENT_JOURNAL")
public class EOJournal extends EOCustObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "JOURNAL_ID")
	private Long journalId;
	
	@Column(name = "JOURNAL_DATE")
	@Temporal(TemporalType.DATE)
	private Date journalDate;
	
	@Column(name = "JOURNAL_QUESTION")
	private String journalQuestion;

	@Column(name = "JOURNAL_ANSWER")
	private String journalAnswer;
	
	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
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
}
