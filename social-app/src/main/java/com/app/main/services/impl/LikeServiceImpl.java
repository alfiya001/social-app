package com.app.main.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.main.models.Like;
import com.app.main.repository.LikeRepository;
import com.app.main.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

	private final LikeRepository likeRepo;

	@Override
	public List<Like> getLikesOfPost(String postId) {
		return likeRepo.findByPostId(postId);
	}

	@Override
	public String likePost(Like like) {
		boolean check = likeRepo.existsByPostIdAndUserId(like.getPost().getId(), like.getUser().getId());
		log.info("existsByPostIdAndUserId " + check);
		if (check)
			return "ALREADY_LIKED";

		like.setCreatedOn(LocalDateTime.now());
		like.setCreatedOn(LocalDateTime.now());
		likeRepo.save(like);
		return "LIKE_SAVE";
	}

	@Override
	public void deleteLikeOfPost(String postId, String userId) {
		Like like = likeRepo.findByPostIdAndUserId(postId, userId);
		likeRepo.delete(like);
	}

}
