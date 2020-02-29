package com.challenge.tweetconsumerproxyservice.service;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;

public interface AuthenticationService {
	String authenticate(LoginUser user) throws IllegalAccessException;
}
