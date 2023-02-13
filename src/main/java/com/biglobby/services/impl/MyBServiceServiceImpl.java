package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.MyBService;
import com.biglobby.repository.MyBServiceRepository;
import com.biglobby.services.MyBServiceService;

@Service
public class MyBServiceServiceImpl implements MyBServiceService {

	@Autowired
	private MyBServiceRepository mbsRepo;

	@Override
	public ResponseEntity<MyBService> get(Long id) {
		Optional<MyBService> myBService = mbsRepo.findById(id);
		if (myBService.isPresent()) {
			return ResponseEntity.ok(myBService.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<MyBService>> get() {
		List<MyBService> myBServices = mbsRepo.findAll();
		return ResponseEntity.ok(myBServices);
	}

	@Override
	public ResponseEntity<MyBService> add(MyBService entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		MyBService added = mbsRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<MyBService> update(Long id, MyBService entity) {
		Optional<MyBService> exist = mbsRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		MyBService updated = mbsRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<MyBService> exist = mbsRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = mbsRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
