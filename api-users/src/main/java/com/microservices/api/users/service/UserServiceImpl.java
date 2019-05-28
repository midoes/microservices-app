package com.microservices.api.users.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.api.users.dto.UserDTO;
import com.microservices.api.users.model.User;
import com.microservices.api.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User user = modelMapper.map(userDTO, User.class);
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userRepository.save(user);
		return userDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(),
					true, true, true, true, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return new ModelMapper().map(user, UserDTO.class);
		} else {
			throw new UsernameNotFoundException(email);
		}
	}

}
