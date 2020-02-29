package com.challenge.tweetconsumerproxyservice.controller;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.TweetData;

import reactor.core.publisher.Flux;

public interface TweetProxy {

	/**
	 * Web metodo con la finalidad de authenticar el usuario y generar un token.
	 * 
	 * @param user
	 * @return Retorna un objeto Json con el token asociado al usuario.
	 * @throws IllegalAccessException
	 */
	String authenticate(LoginUser user) throws IllegalAccessException;

	String receiveTweet(String StatusRequest);

	Flux<TweetData> getTweets();

	TweetData getTweetById(long tweetId);

	TweetData setValid(long tweetId);

	Flux<TweetData> getValidatedTweetsByUserId(long userId);

	Flux<String> getTopHashtags();

}