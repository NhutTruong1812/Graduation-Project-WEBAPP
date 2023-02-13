package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.BServiceHistory; 
import com.biglobby.repository.BServiceHistoryRepository;
import com.biglobby.services.BServiceHistoryService;

@Service
public class BServiceHistoryServiceImpl implements BServiceHistoryService {

	@Autowired
	private BServiceHistoryRepository bshRepo;

	@Override
	public ResponseEntity<BServiceHistory> get(Long id) {
		Optional<BServiceHistory> bServiceHistory = bshRepo.findById(id);
		if (bServiceHistory.isPresent()) {
			return ResponseEntity.ok(bServiceHistory.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<BServiceHistory>> get() {
		List<BServiceHistory> bServiceHistories = bshRepo.findAll();
		return ResponseEntity.ok(bServiceHistories);
	}

	@Override
	public ResponseEntity<BServiceHistory> add(BServiceHistory bserviceHistory) {
		if (bserviceHistory.getId() != null) {
			bserviceHistory.setId(null);
		}
		BServiceHistory added = bshRepo.save(bserviceHistory);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<BServiceHistory> update(Long id, BServiceHistory bserviceHistory) {
		Optional<BServiceHistory> exist = bshRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		bserviceHistory.setId(id);
		BServiceHistory updated = bshRepo.save(bserviceHistory);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<BServiceHistory> exist = bshRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = bshRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAll(Pageable pageable) {
		Optional<Page<BServiceHistory>> BServiceHistory = bshRepo.getListBServiceHistoryAll(pageable);
		return ResponseEntity.ok(BServiceHistory.get());
	}

	@Override
	public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAllSearch(String textSearh, Pageable pageable) {
		Optional<Page<BServiceHistory>> BServiceHistory = bshRepo.getListBServiceHistoryAllSearch(textSearh, pageable);
		return ResponseEntity.ok(BServiceHistory.get());
	}

	@Override
	public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAllById(Long id, Pageable pageable) {
		Optional<Page<BServiceHistory>> BServiceHistory = bshRepo.getListBServiceHistoryAllById(id, pageable);
		return ResponseEntity.ok(BServiceHistory.get());
	}

	@Override
	public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAllByIdSearch(Long id, String textSearh, Pageable pageable) {
		Optional<Page<BServiceHistory>> BServiceHistory = bshRepo.getListBServiceHistoryAllByIdSearch(id, textSearh, pageable);
		return ResponseEntity.ok(BServiceHistory.get());
	}

}
