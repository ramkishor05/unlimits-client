package com.brijframework.client.device.model;

import java.util.UUID;

import org.unlimits.rest.model.UIModel;

public class UIDeviceCoachSession extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String coachDate;
	private UUID coachSession;
	
	private UIDeviceCoachChat coachConversionChat;

	public String getCoachDate() {
		return coachDate;
	}

	public void setCoachDate(String coachDate) {
		this.coachDate = coachDate;
	}

	public UUID getCoachSession() {
		return coachSession;
	}

	public void setCoachSession(UUID coachSession) {
		this.coachSession = coachSession;
	}

	public UIDeviceCoachChat getCoachConversionChat() {
		return coachConversionChat;
	}

	public void setCoachConversionChat(UIDeviceCoachChat coachConversionChat) {
		this.coachConversionChat = coachConversionChat;
	}
	
}
