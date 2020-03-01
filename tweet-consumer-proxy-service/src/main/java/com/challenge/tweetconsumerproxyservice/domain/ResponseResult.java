/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.domain;

import java.time.LocalDateTime;

import com.challenge.tweetconsumerproxyservice.enums.ResponseStatus;

/**
 * @author vvmaster
 *
 */
public class ResponseResult extends AResponseResult<TweetDTO>{
	
	public ResponseResult() {
		super();
	}
	
	public ResponseResult(LocalDateTime date, Iterable<TweetDTO> result, ResponseStatus status, String message) {
		super();
		this.date = date;
		this.result = result;
		this.status = status;
		this.message = message;
	}
}
