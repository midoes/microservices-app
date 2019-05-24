package com.microservices.api.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.microservices.api.users.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
		
}
