/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.domain;

import java.time.LocalDateTime;

import com.challenge.tweetconsumerproxyservice.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author vvmaster
 *
 */
@JsonIgnoreProperties
public abstract class AResponseResult<T> {
	protected LocalDateTime date;
	protected Iterable<T> result;
	protected ResponseStatus status;
	protected String message;

	public AResponseResult() {
		this.date = LocalDateTime.now();
		this.status = ResponseStatus.OK;
		this.setMessage("Exitoso");
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Iterable<T> getResult() {
		return result;
	}

	public void setResult(Iterable<T> result) {
		this.result = result;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(String message, String complementMessage) {
		this.message = new StringBuilder(150).append(message)
				.append("\n\n").append(complementMessage).toString();
	}

}
