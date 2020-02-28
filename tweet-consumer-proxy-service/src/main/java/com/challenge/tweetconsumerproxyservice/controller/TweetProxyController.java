/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.challenge.tweetconsumerproxyservice.AuthenticationFilter;
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

	@Autowired
	AuthenticationFilter authFilter;
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private List<LoginUser> validUsers;
	
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
		String token = authFilter.getToken(user);
		
		if (token != null) {
			return token;
		} else {
			if (validUsers.stream().anyMatch(u -> u.getUserName().equals(user.getUserName())
					&& u.getPassword().equals(user.getPassword()))) {
				token = UUID.randomUUID().toString();
				authFilter.addToken(user, token);
			} else {
				throw new IllegalAccessException("Error: Usuario o password incorrecto!");
			}
		}
		
		return "{\"token\":\"" + token + "\"}";
	}
	
	@Override
	@RequestMapping(value="/tweet", method = {RequestMethod.POST, RequestMethod.PUT})
	public String receiveTweet(@RequestBody String StatusRequest) {
		
		return webClientBuilder.build()
		.post().uri("http://localhost:8081/twitterconsumer/tweet")
		.bodyValue(StatusRequest)
		.retrieve().bodyToMono(String.class).block();
	}
	
	@Override
	@GetMapping("/tweet")
	public Flux<TweetData> getTweets() {
		return webClientBuilder.build()
				.get().uri("http://localhost:8081/twitterconsumer/tweet")
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
	@Override
	@GetMapping("/tweet/{tweetId}")
	public Flux<TweetData> getTweetById(@PathVariable("tweetId") long tweetId) {
		return webClientBuilder.build()
				.get().uri("http://localhost:8081/twitterconsumer/tweet/" + tweetId)
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
	@Override
	@PatchMapping("/tweet/{tweetId}")
	public Flux<TweetData> setValid(@PathVariable("tweetId") long tweetId) {
		return webClientBuilder.build()
				.put().uri("http://localhost:8081/twitterconsumer/tweet/" + tweetId)
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
	@Override
	@GetMapping("/tweets/{userId}")
	public Flux<TweetData> getValidatedTweetsByUserId(@PathVariable("userId") long userId) {
		return webClientBuilder.build()
				.get().uri("http://localhost:8081/twitterconsumer/tweet/" + userId)
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
	@Override
	@GetMapping("/tweet/topmost")
	public Flux<TweetData> getTopHashtags() {
		return webClientBuilder.build()
				.get().uri("http://localhost:8081/twitterconsumer/tweet")
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
	
}
