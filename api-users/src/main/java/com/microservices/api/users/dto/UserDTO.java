package com.microservices.api.users.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {
		
	private static final long serialVersionUID = -8359891099078675737L;
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;	
	private String encryptedPassword;
	private List<AlbumDTO> albums;
	
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
	public List<AlbumDTO> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbumDTO> albums) {
		this.albums = albums;
	}	

}
