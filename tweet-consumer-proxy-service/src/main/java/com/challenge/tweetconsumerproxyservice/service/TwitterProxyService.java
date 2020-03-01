/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.service;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.TweetDTO;

/**
 * @author vvmaster
 *
 */
public interface TwitterProxyService {

	String authenticate(LoginUser user) throws IllegalAccessException;

	String receiveTweet(String StatusRequest);

	Iterable<TweetDTO> getTweets();

	TweetDTO getTweetById(long tweetId);

	TweetDTO setValid(long tweetId);

	Iterable<TweetDTO> getValidatedTweetsByUserId(long userId);

	Iterable<String> getTopHashtags();
}
