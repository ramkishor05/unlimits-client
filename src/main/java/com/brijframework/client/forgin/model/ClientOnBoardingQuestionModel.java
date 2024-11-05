package com.brijframework.client.forgin.model;

import java.util.ArrayList;
import java.util.List;

import org.unlimits.rest.model.UIModel;

public class ClientOnBoardingQuestionModel extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GlobalBoardingQuestionModel question;

	private List<ClientBoardingAnswerModel> answers;
	
	private Long userAccountId;

	public GlobalBoardingQuestionModel getQuestion() {
		if(question==null) {
			question=new GlobalBoardingQuestionModel();
		}
		return question;
	}

	public void setQuestion(GlobalBoardingQuestionModel question) {
		this.question = question;
	}

	public List<ClientBoardingAnswerModel> getAnswers() {
		if(answers==null) {
			answers=new ArrayList<>();
		}
		return answers;
	}

	public void setAnswers(List<ClientBoardingAnswerModel> answers) {
		this.answers = answers;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

}
