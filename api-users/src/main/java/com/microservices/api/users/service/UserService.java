package com.microservices.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.microservices.api.users.dto.UserDTO;

public interface UserService extends UserDetailsService {
	
	public UserDTO createUser(UserDTO userDTO);
	public UserDTO getUserDetailsByEmail(String email);

}
