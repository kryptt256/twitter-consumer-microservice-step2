/**
 * 
 */
package com.challenge.tweetconsumerproxyservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;

/**
 * @author vvmaster
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private List<LoginUser> validUsers;

	@Autowired
	private AuthenticationFilter authFilter;

	@Override
	public String authenticate(LoginUser user) throws IllegalAccessException {
		String token = authFilter.getToken(user);

		if (token != null) {
			return token;
		} else {
			if (validUsers.stream().anyMatch(
					u -> u.getUserName().equals(user.getUserName()) && u.getPassword().equals(user.getPassword()))) {
				token = UUID.randomUUID().toString();
				authFilter.addToken(user, token);
			} else {
				throw new IllegalAccessException("Error: Usuario o password incorrecto!");
			}
		}
		
		return token;
	}

}
