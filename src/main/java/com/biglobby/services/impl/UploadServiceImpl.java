package com.biglobby.services.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biglobby.services.UploadService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private ParamServiceImpl paramService;

	@Autowired
	ServletContext app;

	@Override
	public ResponseEntity<File> save(MultipartFile file, String folder) {
		File saved = paramService.save(file, "/images/" + folder);
		return ResponseEntity.ok(saved);
	}

	@Override
	public ResponseEntity<List<File>> save(MultipartFile[] files, String folder) {
		File dir = new File(app.getRealPath("/images/" + folder));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		List<File> filenames = new ArrayList<>();
		for (MultipartFile file : files) {
			String name = System.currentTimeMillis() + file.getOriginalFilename();
			String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
			try {
				File saveFiled = new File(dir, filename);
				file.transferTo(saveFiled);
				filenames.add(saveFiled);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return ResponseEntity.ok(filenames);
	}

}
