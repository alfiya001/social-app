package com.app.main.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {
	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@JsonIgnore
	@NotBlank
	@Size(max = 120)
	private String password;

	@DBRef
	private Set<Role> roles = new HashSet<>();

	private String name;

	private Gender gender;

	private String bio;

	@NotBlank
	private LocalDateTime DateOfJoin;

	private LocalDate dob;

	private Privacy privacy;
	
//	@DocumentReference(lazy = true, lookup = "{ 'postuser' : ?#{#self._id} }")
	@DBRef
    @ReadOnlyProperty
    private List<Post> posts;
	
	private LocalDateTime updatedOn;


	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, String name, Gender gender, String bio, LocalDate dob) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.bio = bio;
		this.dob = dob;
	}

	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, Set<Role> roles, String name, Gender gender, String bio,
			@NotBlank LocalDateTime dateOfJoin, LocalDate dob, Privacy privacy) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.name = name;
		this.gender = gender;
		this.bio = bio;
		this.DateOfJoin = dateOfJoin;
		this.dob = dob;
		this.privacy = privacy;
	}
}
