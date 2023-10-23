package com.app.main.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.main.models.LoadFile;
import com.app.main.models.Post;
import com.app.main.repository.FileRepository;
import com.app.main.repository.PostRepository;
import com.app.main.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	
	private final FileRepository fileRepository;
	
	private final String PATH = "C:\\Users\\2106421\\OneDrive - Cognizant\\Documents\\alfyaPractice\\Insta\\Insta-demo\\src\\main\\resources\\upload\\";


	@Override
	public List<Post> getAllPost() {
		return postRepository.findAll();
	}

	@Override
	public Post addPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> getPostById(String id) {
		return postRepository.findById(id);
	}

	@Override
	public void deletePost(Post post) {
		postRepository.delete(post);
	}


	@Override
	public LoadFile uploadImage(MultipartFile file) throws IOException {
		String fullPath = PATH + file.getOriginalFilename();
		LoadFile pImage = new LoadFile();
//		int id = service.getSequenceNumber(pImage.SEQUENCE_NAME);
//		pImage.setId(id);
		pImage.setFilename("_" + file.getOriginalFilename());
		pImage.setFileType(file.getContentType());
		pImage.setFilepath(fullPath);

		file.transferTo(new File(fullPath));
		fileRepository.save(pImage);
		return pImage;
	}

	@Override
	public byte[] downloadImage(String fileName) throws IOException {
		Optional<LoadFile> imageObject = fileRepository.findByFilename(fileName);
		log.info("log:" + imageObject.get().getFilepath());

		String fullPath = imageObject.get().getFilepath();
		return Files.readAllBytes(new File(fullPath).toPath());
	}

	@Override
	public List<Post> getPostByUser(String id) {
		return postRepository.getPostByPostuserId(id);
	}
}
