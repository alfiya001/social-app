package com.app.main.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Like {
	
	@Id
	private String id;
	
	private User user;
	
	private Post post;
	
	private LocalDateTime createdOn;

}
