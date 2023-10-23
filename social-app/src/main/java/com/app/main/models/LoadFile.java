package com.app.main.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "loadfile")
public class LoadFile {
	
	@Transient
	public static final String SEQUENCE_NAME = "file_sequence";
	
	@Id
    private String id;
	private String filename;
    private String fileType;
    private String fileSize;
    private String filepath;
    private byte[] file;
}