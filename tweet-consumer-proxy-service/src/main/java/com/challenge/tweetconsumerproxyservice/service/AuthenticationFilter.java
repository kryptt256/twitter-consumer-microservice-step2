/**
 * 
 */
package com.challenge.tweetconsumerproxyservice.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.challenge.tweetconsumerproxyservice.domain.LoginUser;

/**
 * @author vvmaster
 *
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationFilter implements Filter {

	@Value("${proxy.filter.checkauth}")
	private boolean checkAuth;
	private Map<LoginUser, String> activeTokens = new HashMap<>();

	public AuthenticationFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (StringUtils.isBlank( httpRequest.getServletPath() ) || httpRequest.getServletPath().endsWith("authenticate") ) {
			chain.doFilter(req, res);
			return;
		}
		
		String oAuth = httpRequest.getHeader("oAuth");
		
		if (checkAuth && !activeTokens.containsValue(oAuth)) {
			throw new ServletException("Error: Token invalido");
		}

		chain.doFilter(req, res);
	}
	
	public String getToken(LoginUser user) {
		return this.activeTokens.get(user);
	}
	
	public void addToken(LoginUser user, String token) {
		this.activeTokens.put(user, token);
	}

}