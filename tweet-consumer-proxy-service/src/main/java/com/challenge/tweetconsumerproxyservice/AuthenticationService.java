package com.challenge.tweetconsumerproxyservice;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;

public interface AuthenticationService {
	String authenticate(LoginUser user) throws IllegalAccessException;
}
