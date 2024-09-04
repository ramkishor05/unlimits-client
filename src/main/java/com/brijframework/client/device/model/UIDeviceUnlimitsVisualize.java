package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.entities.EOUnlimits;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;

public class UIDeviceUnlimitsVisualize extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String visualizeDate;
	private String visualizeRequest;
	private String visualizeResponse;
	private Integer visualizeYear;
	private Integer errorCode;
	private String errorMsg;
	private String traceMsg;
	@NotNull
	private UnlimitsType type;
	
	@NotNull
	private Long unlimitId;

	@JsonIgnore
	private EOUnlimits eoUnlimits;

	public String getVisualizeDate() {
		return visualizeDate;
	}

	public void setVisualizeDate(String visualizeDate) {
		this.visualizeDate = visualizeDate;
	}

	public String getVisualizeRequest() {
		return visualizeRequest;
	}

	public void setVisualizeRequest(String visualizeRequest) {
		this.visualizeRequest = visualizeRequest;
	}

	public String getVisualizeResponse() {
		return visualizeResponse;
	}

	public void setVisualizeResponse(String visualizeResponse) {
		this.visualizeResponse = visualizeResponse;
	}

	public Integer getVisualizeYear() {
		return visualizeYear;
	}

	public void setVisualizeYear(Integer visualizeYear) {
		this.visualizeYear = visualizeYear;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTraceMsg() {
		return traceMsg;
	}

	public void setTraceMsg(String traceMsg) {
		this.traceMsg = traceMsg;
	}

	public EOUnlimits getEoUnlimits() {
		return eoUnlimits;
	}

	public void setEoUnlimits(EOUnlimits eoUnlimits) {
		this.eoUnlimits = eoUnlimits;
	}

	public UnlimitsType getType() {
		return type;
	}

	public void setType(UnlimitsType type) {
		this.type = type;
	}

	public Long getUnlimitId() {
		return unlimitId;
	}

	public void setUnlimitId(Long unlimitId) {
		this.unlimitId = unlimitId;
	}

}
