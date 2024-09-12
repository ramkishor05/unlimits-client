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
	
	public static UnlimitsType findByType(String type) {
		for (UnlimitsType unlimitsType : values()) {
			if(unlimitsType.type.equalsIgnoreCase(type)) {
				return unlimitsType;
			}
		}
		return null;
	}
}