package com.microservices.api.users.clients;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.api.users.dto.AlbumDTO;

@FeignClient(name="albums-ws", fallback=AlbumsFallback.class)
public interface AlbumsServiceClient {
	
	// Forcing 404 error Bad Request
	@GetMapping("/users/{id}/albums")
	public List<AlbumDTO> getAlbums(@PathVariable String id);
 
}

@Component
class AlbumsFallback implements AlbumsServiceClient {

	@Override
	public List<AlbumDTO> getAlbums(String id) {		 		
		return new ArrayList<AlbumDTO>();
	}

}