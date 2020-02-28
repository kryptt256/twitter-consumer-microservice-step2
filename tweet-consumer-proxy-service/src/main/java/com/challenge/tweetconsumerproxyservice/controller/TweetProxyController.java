/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.challenge.tweetconsumerproxyservice.domain.TweetData;

import reactor.core.publisher.Flux;

/**
 * @author vvmaster
 *
 */
@RestController
@RequestMapping("/twitter")
public class TweetProxyController {

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping(value="/tweet", method = {RequestMethod.POST, RequestMethod.PUT})
	public String receiveTweet(@RequestBody String StatusRequest) {
		return webClientBuilder.build()
		.post().uri("http://localhost:8081/twitterconsumer/tweet")
		.bodyValue(StatusRequest)
		.retrieve().bodyToMono(String.class).block();
	}
	
	@GetMapping("/tweet")
	public Flux<TweetData> getTweets() {
		return webClientBuilder.build()
				.get().uri("http://localhost:8081/twitterconsumer/tweet")
				.retrieve()
				.bodyToFlux(TweetData.class);
	}
}
