package com.challenge.tweetconsumerproxyservice;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;

@SpringBootApplication
@ServletComponentScan
public class TweetConsumerProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetConsumerProxyServiceApplication.class, args);
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}
	
	@Bean
	public List<LoginUser> getValidUsers() {
		return Collections.singletonList(new LoginUser("admin", "123"));
	}

}
