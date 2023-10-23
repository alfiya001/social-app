package com.app.main.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.app.main.models.LoadFile;
import com.app.main.models.Post;

public interface PostService {

	Post addPost(Post post);

	List<Post> getAllPost();

	Optional<Post> getPostById(String id);

	void deletePost(Post post);

	LoadFile uploadImage(MultipartFile file) throws IOException;

	byte[] downloadImage(String fileName) throws IOException;

	List<Post> getPostByUser(String username);

}
