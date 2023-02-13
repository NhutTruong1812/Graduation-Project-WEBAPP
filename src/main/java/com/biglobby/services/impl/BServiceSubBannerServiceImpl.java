package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.BServiceSubBanner;
import com.biglobby.repository.BServiceSubBannerRepository;
import com.biglobby.services.BServiceSubBannerService;

@Service
public class BServiceSubBannerServiceImpl implements BServiceSubBannerService {

	@Autowired
	private BServiceSubBannerRepository sbbRepo;

	@Override
	public ResponseEntity<BServiceSubBanner> get(Long id) {
		Optional<BServiceSubBanner> sbb = sbbRepo.findById(id);
		if (sbb.isPresent()) {
			return ResponseEntity.ok(sbb.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<BServiceSubBanner>> get() {
		List<BServiceSubBanner> sbbs = sbbRepo.findAll();
		return ResponseEntity.ok(sbbs);
	}

	@Override
	public ResponseEntity<BServiceSubBanner> add(BServiceSubBanner entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		BServiceSubBanner added = sbbRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<BServiceSubBanner> update(Long id, BServiceSubBanner entity) {
		Optional<BServiceSubBanner> exist = sbbRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		BServiceSubBanner updated = sbbRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<BServiceSubBanner> exist = sbbRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = sbbRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
