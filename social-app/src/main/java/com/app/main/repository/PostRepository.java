package com.app.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.main.models.Post;

public interface PostRepository extends MongoRepository<Post, String> {

	List<Post> getPostByPostuserId(String id);

}
