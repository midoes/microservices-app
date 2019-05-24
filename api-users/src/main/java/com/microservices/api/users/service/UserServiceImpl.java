package com.microservices.api.users.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.api.users.dto.UserDTO;
import com.microservices.api.users.model.User;
import com.microservices.api.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User user = modelMapper.map(userDTO, User.class);
		user.setEncryptedPassword("test");
		userRepository.save(user);
		return userDTO;
	}
	
}
