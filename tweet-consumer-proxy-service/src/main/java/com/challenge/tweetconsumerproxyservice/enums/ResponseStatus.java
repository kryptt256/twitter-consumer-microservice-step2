/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.enums;

/**
 * @author vvmaster
 *
 */
public enum ResponseStatus {
	ERROR("error"), OK("ok");
	
	private String codStatus;
	
	ResponseStatus(String codStatus) {
		this.codStatus = codStatus;
	}
	
	public String getCodStatus() {
		return this.codStatus;
	}
}
