/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.challenge.tweetconsumerproxyservice.domain.TweetDTO;

import reactor.core.publisher.Flux;

/**
 * @author vvmaster
 *
 */
@Service
public class TwitterProxyServiceImpl implements TwitterProxyService {

	@Value("${consumer.service.base-url}")
	private String consumerServiceBaseUrl;
	
	private AuthenticationService authService;
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	public TwitterProxyServiceImpl(AuthenticationService authService, WebClient.Builder webClientBuilder) {
		this.authService = authService;
		this.webClientBuilder = webClientBuilder;
	}
	
	@Override
	public String authenticate(LoginUser user) throws IllegalAccessException {
		String token = authService.authenticate(user);
		return "{\"token\":\"" + token + "\"}";
	}

	@Override
	public String receiveTweet(String StatusRequest) {
		return webClientBuilder.build()
				.post().uri(consumerServiceBaseUrl + "/tweet")
				.bodyValue(StatusRequest)
				.retrieve().bodyToMono(String.class).block();
	}

	@Override
	public Flux<TweetDTO> getTweets() {
		return webClientBuilder.build()
				.get().uri(consumerServiceBaseUrl + "/tweet")
				.retrieve()
				.bodyToFlux(TweetDTO.class);
	}

	@Override
	public TweetDTO getTweetById(long tweetId) {
		return webClientBuilder.build()
		.get().uri( getUrl("/tweet/", tweetId) )
		.retrieve()
		.bodyToFlux(TweetDTO.class).blockFirst();
	}

	@Override
	public TweetDTO setValid(long tweetId) {
		return (TweetDTO)webClientBuilder.build()
				.patch().uri( getUrl("/tweet/", tweetId) )
				.retrieve()
				.bodyToFlux(TweetDTO.class).blockFirst();
	}

	@Override
	public Flux<TweetDTO> getValidatedTweetsByUserId(long userId) {
		return webClientBuilder.build()
				.get().uri( getUrl("/tweets/", userId) )
				.retrieve()
				.bodyToFlux(TweetDTO.class);
	}

	@Override
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
