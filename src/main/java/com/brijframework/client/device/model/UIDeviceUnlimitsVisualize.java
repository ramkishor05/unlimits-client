package com.brijframework.client.device.model;

import org.unlimits.rest.model.UIModel;

import com.brijframework.client.constants.UnlimitsType;
import com.brijframework.client.entities.EOUnlimits;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class UIDeviceUnlimitsVisualize extends UIModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String visualizeDate;
	private String visualizeRequest;
	private String visualizeResponse;
	private Integer visualizeYear;
	private Long subCategoryId;
	
	private String type;
	
	private Long unlimitsId;
	
	@JsonIgnore
	private EOUnlimits eoUnlimits;
	
	private Integer errorCode;
	private String errorMsg;
	private String traceMsg;
	

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
	public EOUnlimits getEoUnlimits() {
		return eoUnlimits;
	}

	public void setEoUnlimits(EOUnlimits eoUnlimits) {
		this.eoUnlimits = eoUnlimits;
	}

	@JsonIgnore
	public UnlimitsType getUnlimitsType() {
		return UnlimitsType.findByType(type);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUnlimitsId() {
		return unlimitsId;
	}

	public void setUnlimitsId(Long unlimitsId) {
		this.unlimitsId = unlimitsId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode=errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg=errorMsg;
	}

	public void setTraceMsg(String traceMsg) {
		this.traceMsg=traceMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getTraceMsg() {
		return traceMsg;
	}
	
}
