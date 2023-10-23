package com.app.main.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.app.main.models.Followers;
import com.app.main.models.User;
import com.app.main.repository.FollowersRepository;
import com.app.main.repository.UserRepository;
import com.app.main.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	private final FollowersRepository followersRepository;
	
	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> getUserbyUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User updateUser(User user, User userReq) {
		user.setUpdatedOn(LocalDateTime.now());
		if(userReq.getName()!=null) {
			user.setName(user.getName());
		}
		if(userReq.getBio()!=null) user.setBio(userReq.getBio());
		
		if(userReq.getGender() != null) user.setGender(userReq.getGender());
		
		if(userReq.getPrivacy() != null) user.setPrivacy(userReq.getPrivacy());
		
		if(userReq.getUsername() != null) user.setUsername(userReq.getUsername());
		
		return userRepository.save(user);
	}

	@Override
	public void validateUpdate(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Followers addFollower(Followers follower) {
		follower.setCreatedOn(LocalDateTime.now());
		follower.setUpdatedOn(LocalDateTime.now());
		return followersRepository.save(follower);
	}

	@Override
	public List<Followers> getFollowers(String id) {
		return followersRepository.findByTargetuserId(id);
	}

	@Override
	public List<Followers> getFollowings(String id) {
		// TODO Auto-generated method stub
		return followersRepository.findBySourceuser(id);
	}

	@Override
	public String checkIfFollowerExists(@Valid Followers follower) {
		if(followersRepository.existsBySourceuserIdAndTargetuserId (follower.getSourceuser(), follower.getTargetuser())) {
			return "ALREADY_EXISTS";
		}
		return "DOESNOT_EXISTS";
	}
	
	@Override
	public String checkIfFollowingExists(@Valid Followers follower) {
		if(followersRepository.existsByTargetuserIdAndSourceuserId (follower.getTargetuser(), follower.getSourceuser())) {
			return "ALREADY_EXISTS";
		}
		return "DOESNOT_EXISTS";
	}

	@Override
	public long getFollowerCount(User target) {
		return followersRepository.countByTargetuserId(target);
	}

	@Override
	public long getFollowingsCount(User user) {
		return followersRepository.countBySourceuserId(user);
	}

}
