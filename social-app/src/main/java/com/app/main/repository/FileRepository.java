package com.app.main.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.main.models.LoadFile;

public interface FileRepository extends MongoRepository<LoadFile, String> {

	Optional<LoadFile> findByFilename(String fileName);

}
