package com.app.main.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.main.models.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

	List<Comment> findByPostId(String postId);

	Comment findByPostIdAndUserId(String postId, String userId);

}
