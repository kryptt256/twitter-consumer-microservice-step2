package com.challenge.tweetconsumerproxyservice.controller;

import com.challenge.tweetconsumerproxyservice.domain.GenericResult;
import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.ResponseResult;

public interface TweetProxy {

	/**
	 * Web metodo con la finalidad de authenticar el usuario y generar un token.
	 * 
	 * @param user
	 * @return Retorna un objeto Json con el token asociado al usuario.
	 * @throws IllegalAccessException
	 */
	GenericResult authenticate(LoginUser user) throws IllegalAccessException;

	ResponseResult receiveTweet(String StatusRequest);

	ResponseResult getTweets();

	ResponseResult getTweetById(long tweetId);

	ResponseResult setValid(long tweetId);

	ResponseResult getValidatedTweetsByUserId(long userId);

	GenericResult getTopHashtags();

}