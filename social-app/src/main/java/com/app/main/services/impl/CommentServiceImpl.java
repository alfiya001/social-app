package com.app.main.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.main.models.Comment;
import com.app.main.models.Like;
import com.app.main.repository.CommentRepository;
import com.app.main.services.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository comRepo;

	@Override
	public List<Comment> getCommentsOfPost(String postId) {
		return comRepo.findByPostId(postId);
	}

	@Override
	public Comment addcommentPost(Comment comment) {
		comment.setCreatedOn(LocalDateTime.now());
		return comRepo.save(comment);
	}

	@Override
	public void deleteCommentOfPost(String postId, String userId) {
		Comment comment = comRepo.findByPostIdAndUserId(postId, userId);
		comRepo.delete(comment);
	}

	@Override
	public void deletePostComment(String postId) {
		comRepo.deleteAll(comRepo.findByPostId(postId));
		
	}

}
