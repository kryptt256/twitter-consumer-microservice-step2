/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.tweetconsumerproxyservice.domain.GenericResult;
import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.ResponseResult;
import com.challenge.tweetconsumerproxyservice.domain.TweetDTO;
import com.challenge.tweetconsumerproxyservice.enums.ResponseStatus;
import com.challenge.tweetconsumerproxyservice.service.TwitterProxyService;

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
	public GenericResult authenticate(@RequestBody LoginUser user) throws IllegalAccessException {
		GenericResult result = new GenericResult();
		try {
			String response = proxyService.authenticate(user);
			result.setResult(Arrays.asList(response));
			result.setMessage("Autenticado Correctamente");
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error:", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@RequestMapping(value="/tweet", method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseResult receiveTweet(@RequestBody String StatusRequest) {
		ResponseResult result = new ResponseResult();
		
		try {
			proxyService.receiveTweet(StatusRequest);
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error al tratar de consumir el tweet:", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@GetMapping("/tweet")
	public ResponseResult getTweets() {
		ResponseResult result = new ResponseResult();
		
		try {
			Iterable<TweetDTO> tweets = proxyService.getTweets();
			result.setResult(tweets);
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error al obtener los tweets: ", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@GetMapping("/tweet/{tweetId}")
	public ResponseResult getTweetById(@PathVariable("tweetId") long tweetId) {
		
		ResponseResult result = new ResponseResult();
		
		try {
			TweetDTO tweet = proxyService.getTweetById(tweetId);
			result.setResult(Arrays.asList(tweet));
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error: ", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@PatchMapping("/tweet/{tweetId}")
	public ResponseResult setValid(@PathVariable("tweetId") long tweetId) {
		ResponseResult result = new ResponseResult();
		
		try {
			TweetDTO tweet = proxyService.setValid(tweetId);
			result.setResult(Arrays.asList(tweet));
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error: ", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@GetMapping("/tweets/{userId}")
	public ResponseResult getValidatedTweetsByUserId(@PathVariable("userId") long userId) {
		
		ResponseResult result = new ResponseResult();
		
		try {
			Iterable<TweetDTO> tweets = proxyService.getValidatedTweetsByUserId(userId);
			result.setResult(tweets);
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error al buscar los tweets validos: ", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	@GetMapping("/tweet/topmost")
	public GenericResult getTopHashtags() {
		GenericResult result = new GenericResult();
		
		try {
			Iterable<String> tweets = proxyService.getTopHashtags();
			result.setResult(tweets);
		} catch (Exception e) {
			result.setStatus(ResponseStatus.ERROR);
			result.setMessage("Error al buscar los hashtags mas usados: ", e.getMessage());
		}
		
		return result;
	}
	
}
