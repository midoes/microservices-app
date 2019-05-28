package com.microservices.api.users.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.api.users.dto.LoginDTO;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			LoginDTO login = new ObjectMapper().readValue(req.getInputStream(), LoginDTO.class);			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
			return this.getAuthenticationManager().authenticate(authRequest);					
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	}

}
