package com.brijframework.client.constants;
public enum UnlimitsType{
	
    WORDS("WORDS"), IMAGE("IMAGE"), EXAMPLE("EXAMPLE");
	private String type;
	private UnlimitsType(String type) {
		this.type=type;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}