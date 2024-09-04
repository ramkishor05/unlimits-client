package com.brijframework.client.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UnlimitsType {

	@JsonProperty("WORDS")
	WORDS("WORDS"), 
	
	@JsonProperty("IMAGE")
	IMAGE("IMAGE"), 
	
	@JsonProperty("EXAMPLE")
	EXAMPLE("EXAMPLE");

	private String type;

	private UnlimitsType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}