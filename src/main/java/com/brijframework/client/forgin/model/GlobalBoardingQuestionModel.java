package com.brijframework.client.forgin.model;

import org.unlimits.rest.model.UIModel;

public class GlobalBoardingQuestionModel extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String question;
	
	private String hintText;

	private String type;
	
	private boolean required;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	

	public String getHintText() {
		return hintText;
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}
