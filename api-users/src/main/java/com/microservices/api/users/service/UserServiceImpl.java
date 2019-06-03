package com.microservices.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.api.users.clients.AlbumsServiceClient;
import com.microservices.api.users.dto.AlbumDTO;
import com.microservices.api.users.dto.UserDTO;
import com.microservices.api.users.model.User;
import com.microservices.api.users.repository.UserRepository;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	Environment environment;
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@Autowired
	AlbumsServiceClient albumsServiceClient;
	
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

	@Override
	public UserDTO getUserById(String id) {
		Optional<User> user = userRepository.findById(Long.valueOf(id));     
        if(!user.isPresent()) { 
        	throw new UsernameNotFoundException("User not found");
        }        
        ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDetails = modelMapper.map(user.get(), UserDTO.class);       
        
		/*
		String albumsUrl = String.format(environment.getProperty("albums.url"), id);        
        ResponseEntity<List<AlbumDTO>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumDTO>>() {});
        List<AlbumDTO> albumsList = albumsListResponse.getBody();         
        */
		
		/*
		List<AlbumDTO> albumsList = new ArrayList<AlbumDTO>();
		try {
			albumsList = albumsServiceClient.getAlbums(id);
		} catch (FeignException e) {
			logger.error(e.getMessage());
		}
		*/
		
		logger.info("Before calling albums Microservice");
		List<AlbumDTO> albumsList = albumsServiceClient.getAlbums(id);
		logger.info("After calling albums Microservice");
		
        userDetails.setAlbums(albumsList);
		return userDetails;
	}

}
