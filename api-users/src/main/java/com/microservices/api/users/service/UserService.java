package com.microservices.api.users.service;

import com.microservices.api.users.dto.UserDTO;

public interface UserService {
	
	public UserDTO createUser(UserDTO userDTO);

}
