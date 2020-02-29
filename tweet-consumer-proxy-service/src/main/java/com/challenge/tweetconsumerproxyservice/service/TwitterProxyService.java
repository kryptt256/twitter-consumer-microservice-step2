/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.service;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.TweetDTO;

import reactor.core.publisher.Flux;

/**
 * @author vvmaster
 *
 */
public interface TwitterProxyService {

	String authenticate(LoginUser user) throws IllegalAccessException;

	String receiveTweet(String StatusRequest);

	Flux<TweetDTO> getTweets();

	TweetDTO getTweetById(long tweetId);

	TweetDTO setValid(long tweetId);

	Flux<TweetDTO> getValidatedTweetsByUserId(long userId);

	Flux<String> getTopHashtags();
}
