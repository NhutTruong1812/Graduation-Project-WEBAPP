package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.ProductHistory;
import com.biglobby.repository.ProductHistoryRepository;
import com.biglobby.services.ProductHistoryService;

@Service
public class ProductHistoryServiceImpl implements ProductHistoryService {

	@Autowired
	private ProductHistoryRepository pdhRepo;

	@Override
	public ResponseEntity<ProductHistory> get(Long id) {
		Optional<ProductHistory> pdh = pdhRepo.findById(id);
		if (pdh.isPresent()) {
			return ResponseEntity.ok(pdh.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<ProductHistory>> get() {
		List<ProductHistory> pdhs = pdhRepo.findAll();
		return ResponseEntity.ok(pdhs);
	}

	@Override
	public ResponseEntity<ProductHistory> add(ProductHistory entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		ProductHistory added = pdhRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ProductHistory> update(Long id, ProductHistory entity) {
		Optional<ProductHistory> exist = pdhRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		ProductHistory updated = pdhRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<ProductHistory> exist = pdhRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = pdhRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
