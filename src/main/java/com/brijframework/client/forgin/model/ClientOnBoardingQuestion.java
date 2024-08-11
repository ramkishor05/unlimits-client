package com.brijframework.client.forgin.model;

import java.util.ArrayList;
import java.util.List;

import org.unlimits.rest.model.UIModel;

public class ClientOnBoardingQuestion extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GlobalBoardingQuestion question;

	private List<ClientBoardingAnswer> answers;
	
	private Long userAccountId;

	public GlobalBoardingQuestion getQuestion() {
		if(question==null) {
			question=new GlobalBoardingQuestion();
		}
		return question;
	}

	public void setQuestion(GlobalBoardingQuestion question) {
		this.question = question;
	}

	public List<ClientBoardingAnswer> getAnswers() {
		if(answers==null) {
			answers=new ArrayList<>();
		}
		return answers;
	}

	public void setAnswers(List<ClientBoardingAnswer> answers) {
		this.answers = answers;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

}
