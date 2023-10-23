package com.app.main.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.main.models.Followers;
import com.app.main.models.User;

public interface FollowersRepository extends MongoRepository<Followers, String> {

	List<Followers> findByTargetuserId(String id);

	List<Followers> findBySourceuser(String id);

	Boolean existsBySourceuserIdAndTargetuserId(User sourceuser, User targetuser);

	boolean existsByTargetuserIdAndSourceuserId(User targetuser, User sourceuser);

	long countByTargetuserId(User targetuser);

	long countBySourceuserId(User user);

}
