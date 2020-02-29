package com.challenge.tweetconsumerproxyservice.controller.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TweetProxyControllerTest {

	@Autowired
	MockMvc mvc;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void itShouldAuthenticateUser() throws Exception {
		LoginUser user = new LoginUser("admin", "123");
		String jsonStr;
		jsonStr = mapper.writeValueAsString(user);
		
		ResultActions result = mvc.perform(post("/twitter/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(jsonStr));
		
		assertTrue(result.andReturn().getResponse().getContentAsString().contains("token"));
		result.andExpect(status().isOk());
	}
	
	@Test
	void itShouldNotAuthenticateUser() throws Exception {
		LoginUser user = new LoginUser("admin", "123433");
		String jsonStr;
		jsonStr = mapper.writeValueAsString(user);

		Exception exception = assertThrows(
				NestedServletException.class, 
				() -> mvc.perform(post("/twitter/authenticate").contentType(MediaType.APPLICATION_JSON)
						.content(jsonStr)).andExpect(status().isOk()) );

	        assertTrue(exception.getCause().getMessage().contains("incorrecto"));
	}
	
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
