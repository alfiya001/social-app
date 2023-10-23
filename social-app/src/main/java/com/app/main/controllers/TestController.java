package com.app.main.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.main.models.Post;
import com.app.main.services.PostService;

import lombok.RequiredArgsConstructor;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

	private final PostService postService;

//	@PostMapping("/post")
//	public ResponseEntity<Post> createProduct(@RequestBody Post post) {
////		post.setId(service.getSequenceNumber(post.SEQUENCE_NAME));
//		post.setCreatedAt(LocalDateTime.now());
//		post.setUpdatedAt(LocalDateTime.now());
//		return ResponseEntity.ok().body(postService.addPost(post));
//	}

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/post")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getPost() {
		
		List<Post> posts = postService.getAllPost();
		if(posts.isEmpty())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(posts);
    }
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
