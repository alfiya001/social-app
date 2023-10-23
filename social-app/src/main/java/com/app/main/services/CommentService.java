package com.app.main.services;

import java.util.List;

import com.app.main.models.Comment;
import com.app.main.models.Like;

public interface CommentService {

	List<Comment> getCommentsOfPost(String postId);

	Comment addcommentPost(Comment like);

	void deleteCommentOfPost(String postId, String userId);

	void deletePostComment(String postId);

}
