package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.BCoinHistory; 
import com.biglobby.repository.BCoinHistoryRepository;
import com.biglobby.services.BCoinHistoryService;

@Service
public class BCoinHistoryServiceImpl implements BCoinHistoryService {

	@Autowired
	private BCoinHistoryRepository bCoinHisRepo;

	@Override
	public ResponseEntity<BCoinHistory> get(Long id) {
		Optional<BCoinHistory> bCoinHistory = bCoinHisRepo.findById(id);
		if (bCoinHistory.isPresent()) {
			return ResponseEntity.ok(bCoinHistory.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<BCoinHistory>> get() {
		List<BCoinHistory> bCoinHistories = bCoinHisRepo.findAll();
		return ResponseEntity.ok(bCoinHistories);
	}

	@Override
	public ResponseEntity<BCoinHistory> add(BCoinHistory bcoinHistory) {
		if (bcoinHistory.getId() != null) {
			bcoinHistory.setId(null);
		}
		BCoinHistory added = bCoinHisRepo.save(bcoinHistory);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<BCoinHistory> update(Long id, BCoinHistory bcoinHistory) {
		Optional<BCoinHistory> exist = bCoinHisRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		bcoinHistory.setId(id);
		BCoinHistory updated = bCoinHisRepo.save(bcoinHistory);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<BCoinHistory> exist = bCoinHisRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = bCoinHisRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Page<BCoinHistory>> getListBCoinHistoryAll(Pageable pageable) {
		Optional<Page<BCoinHistory>> BCoinHistory = bCoinHisRepo.getListBCoinHistoryAll(pageable);
		return ResponseEntity.ok(BCoinHistory.get());
	}

	@Override
	public ResponseEntity<Page<BCoinHistory>> getListBCoinHistoryAllSearch(String textSearh, Pageable pageable) {
		Optional<Page<BCoinHistory>> BCoinHistory = bCoinHisRepo.getListBCoinHistoryAllSearch(textSearh, pageable);
		return ResponseEntity.ok(BCoinHistory.get());
	}

}
