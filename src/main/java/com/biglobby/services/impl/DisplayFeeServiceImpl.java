package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.stereotype.Service;

import com.biglobby.entity.DisplayFee;
import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.repository.DisplayFeeHistoryRepository;
import com.biglobby.repository.DisplayFeeRepository; 
import com.biglobby.services.DisplayFeeService;

@Service
public class DisplayFeeServiceImpl implements DisplayFeeService {

	@Autowired
	private DisplayFeeRepository dfRepo;

	@Autowired
	private DisplayFeeHistoryRepository dfhRepo;
 
	@Override
	public ResponseEntity<DisplayFee> get(Long id) {
		Optional<DisplayFee> displayFee = dfRepo.findById(id);
		if (displayFee.isPresent()) {
			return ResponseEntity.ok(displayFee.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<DisplayFee>> get() {
		List<DisplayFee> displayFees = dfRepo.findAll();
		return ResponseEntity.ok(displayFees);
	}

	@Override
	public ResponseEntity<DisplayFee> add(DisplayFee displayFee) {
		if (displayFee.getId() != null) {
			displayFee.setId(null);
		}
		DisplayFee added = dfRepo.save(displayFee);
		if (added != null) { 
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<DisplayFee> update(Long id, DisplayFee displayFee) {
		Optional<DisplayFee> exist = dfRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		displayFee.setId(id);
		DisplayFee updated = dfRepo.save(displayFee);
		if (updated != null) { 
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<DisplayFee> exist = dfRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = dfRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) { 
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<DisplayFeeHistory>> getHistories(Long dfId) {
		Optional<DisplayFee> displayfee = dfRepo.findById(dfId);
		if (!displayfee.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(displayfee.get().getDisplayFeeHistories());
	}

	@Override
	public ResponseEntity<DisplayFeeHistory> getHistory(Long hid) {
		Optional<DisplayFeeHistory> dfh = dfhRepo.findById(hid);
		if (dfh.isPresent()) {
			return ResponseEntity.ok(dfh.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<DisplayFeeHistory> addHistory(DisplayFeeHistory history) {
		if (history.getId() != null) {
			history.setId(null);
		}
		DisplayFeeHistory added = dfhRepo.save(history);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<DisplayFeeHistory> updateHistory(Long hid, DisplayFeeHistory history) {
		Optional<DisplayFeeHistory> exist = dfhRepo.findById(hid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		history.setId(hid);
		DisplayFeeHistory updated = dfhRepo.save(history);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteHistory(Long hid) {
		Optional<DisplayFeeHistory> exist = dfhRepo.findById(hid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = dfhRepo.removeById(hid);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Page<DisplayFee>> getPage(Integer pagenum, Integer limit) {
		Pageable pageable = PageRequest.of(pagenum, limit, Sort.by("id").ascending());
		Page<DisplayFee> page = dfRepo.findAll(pageable);
		return ResponseEntity.ok(page);
	}

	@Override
	public ResponseEntity<DisplayFee> findByPriceInRange(Double price) {
		Optional<DisplayFee> displayfee = dfRepo.findByPriceInRange(price);
		if (displayfee.isPresent()) {
			return ResponseEntity.ok(displayfee.get());
		}
		return ResponseEntity.notFound().build();
	}

}
