package com.microservices.api.users.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.api.users.dto.LoginDTO;
import com.microservices.api.users.dto.UserDTO;
import com.microservices.api.users.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment environment;
	
	public AuthenticationFilter(UserService userService, Environment environment,
			AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			LoginDTO login = new ObjectMapper().readValue(req.getInputStream(), LoginDTO.class);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(login.getEmail(),
					login.getPassword());
			return this.getAuthenticationManager().authenticate(authRequest);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User)authResult.getPrincipal()).getUsername();		
		UserDTO userDetails = userService.getUserDetailsByEmail(username);
		String token = Jwts.builder()
				.setSubject(userDetails.getId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
				.signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secret"))
				.compact();
		response.addHeader("token", token);
		response.addHeader("id", userDetails.getId());
	}

}
