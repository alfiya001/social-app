package com.app.main.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.main.models.Like;
import com.app.main.models.Post;
import com.app.main.service.LikeService;
import com.app.main.services.PostService;

import lombok.RequiredArgsConstructor;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostsController {

	private final PostService postService;

	@GetMapping("/post")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getPost() {
		
		List<Post> posts = postService.getAllPost();
		if(posts.isEmpty())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(posts);
    }
	
	@PostMapping("/post")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Post> createProduct(@RequestBody Post post) {
//		post.setId(service.getSequenceNumber(post.SEQUENCE_NAME));
		post.setCreatedAt(LocalDateTime.now());
		post.setUpdatedAt(LocalDateTime.now());
		return ResponseEntity.ok().body(postService.addPost(post));
	}

	@PutMapping("/post/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> updatePost(@RequestBody Post post, @PathVariable String id) {

		Post postD = postService.getPostById(id).get();
		if (postService.getPostById(id).isPresent()) {
			postD.setCaption(post.getCaption());
			postD.setCommentCount(post.getCommentCount());
			postD.setLikecount(post.getLikecount());
			postD.setUpdatedAt(LocalDateTime.now());
			return ResponseEntity.ok().body(postService.addPost(postD));
		}
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/post/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getPostbyId(@PathVariable String id) {
		Optional<Post> post = postService.getPostById(id);
		if (post.isPresent())
			return ResponseEntity.ok().body(post);
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/post/user/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getPostbyUsername(@PathVariable String id) {
		List<Post> post = postService.getPostByUser(id);
		if (!post.isEmpty())
			return ResponseEntity.ok().body(post);
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/post/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deletePost(@PathVariable String id) {
		Optional<Post> post = postService.getPostById(id);
		if (!post.isPresent())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);

		postService.deletePost(post.get());
		return ResponseEntity.ok().body(HttpStatus.NO_CONTENT);
	}

    @ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/post/upload")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file) throws IOException{
    	return ResponseEntity.status(HttpStatus.OK).body(postService.uploadImage(file));
	}
	
	@GetMapping("/post/download/{fileName}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) throws IOException {
		byte[] image = postService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

//	@GetMapping("/post")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public ResponseEntity<?> getPost() {
//
//		List<Post> posts = postService.getAllPost();
//		if (posts.isEmpty())
//			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//		return ResponseEntity.ok().body(posts);
//	}


}
