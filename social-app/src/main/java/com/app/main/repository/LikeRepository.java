package com.app.main.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.main.models.Like;

public interface LikeRepository extends MongoRepository<Like, String> {

	List<Like> findByPostId(String postId);

	void deleteAllById(String postId);

	boolean existsByPostIdAndUserId(String id, String id2);

	Like findByPostIdAndUserId(String postId, String userId);

}
