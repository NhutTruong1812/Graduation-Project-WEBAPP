package com.biglobby.rest.controller;

import java.io.File;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biglobby.services.UploadService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/upload")
public class UploadRestController {

	@Autowired
	private UploadService uploadService;

	
//	@PostMapping("/{folder}")
//	public ResponseEntity<File> post(@RequestBody MultipartFile file, @PathVariable("folder") String folder) {
//		return uploadService.save(file, folder);
//	}

	@PostMapping("/{folder}")
	public ResponseEntity<List<File>> uploads(@PathParam("file") MultipartFile[] file,
			@PathVariable("folder") String folder) {
		return uploadService.save(file, folder);
	}
}
