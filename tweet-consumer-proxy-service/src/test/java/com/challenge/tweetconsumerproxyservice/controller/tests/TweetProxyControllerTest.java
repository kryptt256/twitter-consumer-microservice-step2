package com.challenge.tweetconsumerproxyservice.controller.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TweetProxyControllerTest {

	@Autowired
	MockMvc mvc;
	
	@Test
	void itShouldSendTweetRequest() throws Exception {
		File resource = new ClassPathResource("/statusrequest.json").getFile();
		String jsonStr = new String(Files.readAllBytes(resource.toPath()));
		
		mvc.perform( post("/twitter/tweet").contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr)).andExpect( status().isOk());
	}
	
	@Test
	void itShouldRetrieveTweets() throws Exception {
		mvc.perform(get("/twitter/tweet")).andExpect(status().isOk());
	}

}
