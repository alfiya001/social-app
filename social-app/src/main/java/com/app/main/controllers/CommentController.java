package com.app.main.controllers;

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

import com.app.main.models.Comment;
import com.app.main.models.Post;
import com.app.main.models.User;
import com.app.main.service.LikeService;
import com.app.main.services.CommentService;
import com.app.main.services.PostService;
import com.app.main.services.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
	
	private final PostService postService;
	
	private final UserService userService;
	
	private final CommentService comService;

	@GetMapping("/comment/post/{postId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getCommentsOfPost(@PathVariable String postId) {
		
		List<Comment> comments = comService.getCommentsOfPost(postId);
		if(comments.isEmpty())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(comments);
    }
	
	@PostMapping("/comment/post/{postId}/user/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addcommentPost(@RequestBody Comment comment, @PathVariable String postId, @PathVariable String userId) {
		Post post = postService.getPostById(postId).get();
		post.setCommentCount(post.getCommentCount() + 1);
		postService.addPost(post);
		
		User user = userService.getUserById(userId).get();
		
//		Comment comment = new Comment();
		comment.setUser(user);
		comment.setPost(post);
//		comment.setDescription(comment.getDescription());
		
		return ResponseEntity.ok(comService.addcommentPost(comment));
	}
	
	@DeleteMapping("/comment/post/{postId}/user/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserComment(@PathVariable String postId, @PathVariable String userId) {
		
		comService.deleteCommentOfPost(postId, userId);
			return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
	
	@DeleteMapping("/comment/post/{postId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePostComment(@PathVariable String postId) {
		
		comService.deletePostComment(postId);
			return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
