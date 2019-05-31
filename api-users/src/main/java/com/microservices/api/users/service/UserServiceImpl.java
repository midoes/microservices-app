package com.microservices.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

@Service
public class UserServiceImpl implements UserService {

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
		
		List<AlbumDTO> albumsList = albumsServiceClient.getAlbums(id);
		
        userDetails.setAlbums(albumsList);
		return userDetails;
	}

}
