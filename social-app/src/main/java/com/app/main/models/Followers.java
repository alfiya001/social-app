package com.app.main.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Followers {

	@Id
	private String id;
	
//	@NotBlank
	@DBRef
	private User sourceuser;
	
//	@NotBlank
	@DBRef
	private User targetuser;
	
//	@NotBlank
	private LocalDateTime createdOn;
	
//	@NotBlank
	private LocalDateTime updatedOn;
}
