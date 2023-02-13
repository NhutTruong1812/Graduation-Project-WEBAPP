package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.repository.DisplayFeeHistoryRepository;
import com.biglobby.services.DisplayFeeHistoryService;

@Service
public class DisplayFeeHistoryServiceImpl implements DisplayFeeHistoryService {

	@Autowired
	private DisplayFeeHistoryRepository dfhRepo;

	@Override
	public ResponseEntity<DisplayFeeHistory> get(Long id) {
		Optional<DisplayFeeHistory> dfh = dfhRepo.findById(id);
		if (dfh.isPresent()) {
			return ResponseEntity.ok(dfh.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<DisplayFeeHistory>> get() {
		List<DisplayFeeHistory> dfhs = dfhRepo.findAll();
		return ResponseEntity.ok(dfhs);
	}

	@Override
	public ResponseEntity<DisplayFeeHistory> add(DisplayFeeHistory entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		DisplayFeeHistory added = dfhRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<DisplayFeeHistory> update(Long id, DisplayFeeHistory entity) {
		Optional<DisplayFeeHistory> exist = dfhRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		DisplayFeeHistory updated = dfhRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<DisplayFeeHistory> exist = dfhRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = dfhRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.notFound().build();
	}

}
