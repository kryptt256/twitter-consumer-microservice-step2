/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.challenge.tweetconsumerproxyservice.AuthenticationService;
import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.TweetData;

import reactor.core.publisher.Flux;

/**
 * @author vvmaster
 *
 */
@RestController
@RequestMapping("/twitter")
public class TweetProxyController implements TweetProxy {

	@Value("${consumer.service.base-url}")
	private String consumerServiceBaseUrl;
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
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
		String token = authService.authenticate(user);
		return "{\"token\":\"" + token + "\"}";
	}
	
	@Override
	@RequestMapping(value="/tweet", method = {RequestMethod.POST, RequestMethod.PUT})
	public String receiveTweet(@RequestBody String StatusRequest) {
		
		return webClientBuilder.build()
		.post().uri(consumerServiceBaseUrl + "/tweet")
		.bodyValue(StatusRequest)
		.retrieve().bodyToMono(String.class).block();
	}
	
	@Override
	@GetMapping("/tweet")
	public Flux<TweetData> getTweets() {
		return webClientBuilder.build()
				.get().uri(consumerServiceBaseUrl + "/tweet")
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
	@Override
	@GetMapping("/tweet/{tweetId}")
	public TweetData getTweetById(@PathVariable("tweetId") long tweetId) {
		return webClientBuilder.build()
				.get().uri( getUrl("/tweet/", tweetId) )
				.retrieve()
				.bodyToFlux(TweetData.class).blockFirst();
	}
	
	@Override
	@PatchMapping("/tweet/{tweetId}")
	public TweetData setValid(@PathVariable("tweetId") long tweetId) {
		return (TweetData)webClientBuilder.build()
				.patch().uri( getUrl("/tweet/", tweetId) )
				.retrieve()
				.bodyToFlux(TweetData.class).blockFirst();
	}
	
	@Override
	@GetMapping("/tweets/{userId}")
	public Flux<TweetData> getValidatedTweetsByUserId(@PathVariable("userId") long userId) {
		return webClientBuilder.build()
				.get().uri( getUrl("/tweets/", userId) )
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
	@Override
	@GetMapping("/tweet/topmost")
	public Flux<String> getTopHashtags() {
		return webClientBuilder.build()
				.get().uri(consumerServiceBaseUrl + "/tweet/topmost")
				.retrieve()
				.bodyToFlux(String.class);
	}
	
	private String getUrl(String path, long value) {
		return consumerServiceBaseUrl + path + value;
	}
	
}
