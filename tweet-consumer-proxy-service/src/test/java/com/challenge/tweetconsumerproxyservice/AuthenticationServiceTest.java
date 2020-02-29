package com.challenge.tweetconsumerproxyservice;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;

@SpringBootTest
class AuthenticationServiceTest {

	@Autowired
	private AuthenticationService service;
	
	@Test
	void itShouldAutheticate() {
		LoginUser user = new LoginUser("admin", "123");
		assertDoesNotThrow(() -> service.authenticate(user));
	}
	
	@Test
	void itShouldNotAuthenticate() {
		LoginUser user = new LoginUser("admin", "564645564");
		assertThrows(IllegalAccessException.class, () -> service.authenticate(user));
		
	}

}
