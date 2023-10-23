package com.app.main.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
	
	@Id
	private String id;
	
	@NotBlank
	@Size(max = 5000)
	private String description;
	
	private User user;
	
	private Post post;
	
	private LocalDateTime createdOn;

}
