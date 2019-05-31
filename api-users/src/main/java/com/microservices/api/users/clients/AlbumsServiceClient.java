package com.microservices.api.users.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.api.users.dto.AlbumDTO;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {
	
	@GetMapping("/users/{id}/albumss")
	public List<AlbumDTO> getAlbums(@PathVariable String id);
 
}
