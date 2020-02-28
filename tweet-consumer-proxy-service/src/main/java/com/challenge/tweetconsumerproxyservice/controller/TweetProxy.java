package com.challenge.tweetconsumerproxyservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	Flux<TweetData> getTweetById(long tweetId);

	Flux<TweetData> setValid(long tweetId);

	Flux<TweetData> getValidatedTweetsByUserId(long userId);

	Flux<TweetData> getTopHashtags();

}