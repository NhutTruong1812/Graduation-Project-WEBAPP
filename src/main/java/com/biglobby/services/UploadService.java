package com.biglobby.services;

import java.io.File;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	public ResponseEntity<File> save(MultipartFile file, String folder);

	public ResponseEntity<List<File>> save(MultipartFile[] files, String folder);
}
