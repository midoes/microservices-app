package com.microservices.api.users.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.api.users.dto.UserDTO;
import com.microservices.api.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/status/check")
	public String status() {
		return "Working on port: "+env.getProperty("local.server.port");
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {		
		UserDTO user = userService.createUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
}
