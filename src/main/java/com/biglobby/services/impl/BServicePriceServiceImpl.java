package com.biglobby.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.BServicePrice;
import com.biglobby.repository.BServicePriceRepository;
import com.biglobby.services.BServicePriceService;

@Service
public class BServicePriceServiceImpl implements BServicePriceService {

	@Autowired
	private BServicePriceRepository bspRepo;

	@Override
	public ResponseEntity<BServicePrice> get(Long id) {
		Optional<BServicePrice> bServicePrice = bspRepo.findById(id);
		if (bServicePrice.isPresent()) {
			return ResponseEntity.ok(bServicePrice.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<BServicePrice>> get() {
		List<BServicePrice> bServicePrices = bspRepo.findAll();
		return ResponseEntity.ok(bServicePrices);
	}

	@Override
	public ResponseEntity<BServicePrice> add(BServicePrice bservicePrice) {
		if (bservicePrice.getId() != null) {
			bservicePrice.setId(null);
		}
		bservicePrice.setPriceDate(new Timestamp(new Date().getTime()));
		BServicePrice added = bspRepo.save(bservicePrice);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<BServicePrice> update(Long id, BServicePrice bservicePrice) {
		Optional<BServicePrice> exist = bspRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		bservicePrice.setId(id);
		BServicePrice updated = bspRepo.save(bservicePrice);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<BServicePrice> exist = bspRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = bspRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<BServicePrice>> findByBserviceId(Long id) {
		List<BServicePrice> bServicePrice = bspRepo.findByBserviceId(id);
		return ResponseEntity.ok(bServicePrice);
	}
}
