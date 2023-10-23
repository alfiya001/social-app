package com.app.main.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.app.main.models.Followers;
import com.app.main.models.User;

public interface UserService {

	List<User> getAllUser();

	Optional<User> getUserById(String id);

	Optional<User> getUserbyUsername(String username);

	User updateUser(User user, User userReq);

	void validateUpdate(User user);

	User addUser(User user);

	Followers addFollower(Followers follower);

	List<Followers> getFollowers(String id);

	List<Followers> getFollowings(String id);

	String checkIfFollowerExists(@Valid Followers follower);
	
	String checkIfFollowingExists(Followers follower);

	long getFollowerCount(User source);

	long getFollowingsCount(User user);

}
