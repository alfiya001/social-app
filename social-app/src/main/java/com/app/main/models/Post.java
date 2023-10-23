package com.app.main.models;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class Post {
	
//	@Transient
//	public static final String SEQUENCE_NAME = "user_sequence";
	
	@Id
	private String id;
	
	private long likecount;
	
	private long commentCount;
	
    @Size(max = 100)
	private String caption;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

	@DBRef
	@JsonIgnore
	private User postuser;
    
//    private String image;
    
}
