/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.TweetDTO;
import com.challenge.tweetconsumerproxyservice.service.TwitterProxyService;

import reactor.core.publisher.Flux;

/**
 * @author vvmaster
 *
 */
@RestController
@RequestMapping("/twitter")
public class TweetProxyController implements TweetProxy {

	@Autowired
	private TwitterProxyService proxyService;
	
	/**
	 * Web metodo con la finalidad de authenticar el usuario y generar un token.
	 * 
	 * @param user
	 * @return Retorna un objeto Json con el token asociado al usuario.
	 * @throws IllegalAccessException
	 */
	@Override
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String authenticate(@RequestBody LoginUser user) throws IllegalAccessException {
		return proxyService.authenticate(user);
	}
	
	@Override
	@RequestMapping(value="/tweet", method = {RequestMethod.POST, RequestMethod.PUT})
	public String receiveTweet(@RequestBody String StatusRequest) {
		return proxyService.receiveTweet(StatusRequest);
	}
	
	@Override
	@GetMapping("/tweet")
	public Flux<TweetDTO> getTweets() {
		return proxyService.getTweets();
	}
	
	@Override
	@GetMapping("/tweet/{tweetId}")
	public TweetDTO getTweetById(@PathVariable("tweetId") long tweetId) {
		return proxyService.getTweetById(tweetId);
	}
	
	@Override
	@PatchMapping("/tweet/{tweetId}")
	public TweetDTO setValid(@PathVariable("tweetId") long tweetId) {
		return proxyService.setValid(tweetId);
	}
	
	@Override
	@GetMapping("/tweets/{userId}")
	public Flux<TweetDTO> getValidatedTweetsByUserId(@PathVariable("userId") long userId) {
		return proxyService.getValidatedTweetsByUserId(userId);
	}
	
	@Override
	@GetMapping("/tweet/topmost")
	public Flux<String> getTopHashtags() {
		return proxyService.getTopHashtags();
	}
	
}
