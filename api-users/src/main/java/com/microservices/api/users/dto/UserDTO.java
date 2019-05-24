package com.microservices.api.users.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO implements Serializable {
		
	private static final long serialVersionUID = -8359891099078675737L;
	
	private String id;
	@NotNull
	@Size(min = 2)
	private String firstName;
	@NotNull
	@Size(min = 2)
	private String lastName;
	@NotNull
	@Size(min = 8, max = 16)
	private String password;
	@Email
	@NotNull
	private String email;	
	private String encryptedPassword;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}	

}
