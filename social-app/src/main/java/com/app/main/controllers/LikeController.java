package com.app.main.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.main.models.Like;
import com.app.main.models.Post;
import com.app.main.models.User;
import com.app.main.service.LikeService;
import com.app.main.services.PostService;
import com.app.main.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
	
	private final PostService postService;
	
	private final UserService userService;
	
	private final LikeService likeService;

	@GetMapping("/like/post/{postId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getLikesOfPost(@PathVariable String postId) {
		
		List<Like> likes = likeService.getLikesOfPost(postId);
		if(likes.isEmpty())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(likes);
    }
	
	@PostMapping("/like/post/{postId}/user/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addlikePost(@PathVariable String postId, @PathVariable String userId) {
		Post post = postService.getPostById(postId).get();
		post.setLikecount(post.getLikecount() + 1);
		postService.addPost(post);
		
		User user = userService.getUserById(userId).get();
		
		Like like = new Like();
		like.setUser(user);
		like.setPost(post);
		String likePost = likeService.likePost(like);
		if(likePost == "LIKE_SAVE")
			return ResponseEntity.ok().body(likePost);
		
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(likePost);
	}
	
	@DeleteMapping("/like/post/{postId}/user/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteLike(@PathVariable String postId, @PathVariable String userId) {
		
		likeService.deleteLikeOfPost(postId, userId);
			return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
