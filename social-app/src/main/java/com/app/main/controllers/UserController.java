package com.app.main.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.main.models.Followers;
import com.app.main.models.Post;
import com.app.main.models.User;
import com.app.main.payload.request.LoginRequest;
import com.app.main.repository.UserRepository;
import com.app.main.services.PostService;
import com.app.main.services.UserService;

import lombok.RequiredArgsConstructor;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	private final UserRepository userRepository;

	private final PasswordEncoder encoder;

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllUser() {

		List<User> users = userService.getAllUser();
		if (users.isEmpty())
			return ResponseEntity.ok(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserbyId(@PathVariable String id) {
		Optional<User> user = userService.getUserById(id);
		if (user.isPresent())
			return ResponseEntity.ok().body(user);
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserbyUsername(@PathVariable String username) {
		Optional<User> user = userService.getUserbyUsername(username);
		if (user.isPresent())
			return ResponseEntity.ok().body(user);
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/user/changePass")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changePassword(@RequestBody LoginRequest loginREquest) {

		Optional<User> userOpt = userService.getUserbyUsername(loginREquest.getUsername());
		User user = userOpt.get();
		if (userOpt.isPresent()) {
			user.setPassword(encoder.encode(loginREquest.getPassword()));

			return ResponseEntity.ok().body(userService.addUser(user));
		}
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/user/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> editUserProfile(@RequestBody User userReq, @PathVariable String id) {

		Optional<User> userOpt = userService.getUserById(id);
		User user = userOpt.get();
		if (userOpt.isPresent()) {
//			userService.validateUpdate(user);

			return ResponseEntity.ok().body(userService.updateUser(user, userReq));
		}
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	/* ----------------------Followers APIs------------------------------- */

	@PostMapping("/follower")
	public ResponseEntity<?> addFollower(@Valid @RequestBody Followers follower) {

		if (userRepository.existsById(follower.getSourceuser().getId())
				&& userRepository.existsById(follower.getTargetuser().getId())) {
			String checkIfFollower = userService.checkIfFollowerExists(follower);
			if(checkIfFollower=="DOESNOT_EXISTS")
				return ResponseEntity.ok().body(userService.addFollower(follower));
			return ResponseEntity.ok(HttpStatus.ALREADY_REPORTED);
		}
		return ResponseEntity.ok(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/followers/target/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserFollowers(@PathVariable String id) {
		List<Followers> followersList = userService.getFollowers(id);
		if (!followersList.isEmpty())
			return ResponseEntity.ok().body(followersList);
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/followings/source/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserFollowings(@PathVariable String id) {
		List<Followers> followersList = userService.getFollowings(id);
		if (!followersList.isEmpty())
			return ResponseEntity.ok().body(followersList);
		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/followers/count/{target}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> followerCount(@PathVariable String target) {
		User user = userService.getUserById(target).get();
		long fCount = userService.getFollowerCount(user);
//		if (!followersList.isEmpty())
			return ResponseEntity.ok().body(fCount);
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/followings/count/{source}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> followingsCount(@PathVariable String source) {
		User user = userService.getUserById(source).get();
		long fCount = userService.getFollowingsCount(user);
//		if (!followersList.isEmpty())
			return ResponseEntity.ok().body(fCount);
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
	}
	
//	@GetMapping("/followings/source/{source}/target/{target}")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public ResponseEntity<?> checkFollowingstatus(@PathVariable String source, @PathVariable String target) {
//		List<Followers> followersList = userService.checkIfFollowerExists(source, target);
//		if (!followersList.isEmpty())
//			return ResponseEntity.ok().body(followersList);
//		return ResponseEntity.ok(HttpStatus.NOT_FOUND);
//	}

}
