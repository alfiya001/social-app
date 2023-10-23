package com.app.main.service;

import java.util.List;

import com.app.main.models.Like;

public interface LikeService {

	List<Like> getLikesOfPost(String postId);

	String likePost(Like like);

	void deleteLikeOfPost(String postId, String userId);

}
