package com.microservices.api.users.service;

import java.util.UUID;

import com.microservices.api.users.dto.UserDTO;

public class UserServiceImpl implements UserService {
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		userDTO.setId(UUID.randomUUID().toString());
		return userDTO;
	}
	
}
