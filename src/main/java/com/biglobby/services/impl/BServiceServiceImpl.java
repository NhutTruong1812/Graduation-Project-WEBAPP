package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.BService;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.BServicePrice;
import com.biglobby.entity.BServiceSubBanner;
import com.biglobby.entity.MyBService;
import com.biglobby.repository.BServiceRepository;
import com.biglobby.services.BServiceService;

@Service
public class BServiceServiceImpl implements BServiceService {

	@Autowired
	private BServiceRepository bserviceRepo;

	@Override
	public ResponseEntity<BService> get(Long id) {
		Optional<BService> bService = bserviceRepo.findById(id);
		if (bService.isPresent()) {
			return ResponseEntity.ok(bService.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<BService>> get() {
		List<BService> bServices = bserviceRepo.findAll();
		return ResponseEntity.ok(bServices);
	}

	@Override
	public ResponseEntity<BService> add(BService bservice) {
		if (bservice.getId() != null) {
			bservice.setId(null);
		}
		BService added = bserviceRepo.save(bservice);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<BService> update(Long id, BService bservice) {
		Optional<BService> exist = bserviceRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		bservice.setId(id);
		BService updated = bserviceRepo.save(bservice);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<BService> exist = bserviceRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = bserviceRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<MyBService>> getMyBServices(Long bServiceId) {
		Optional<BService> bservice = bserviceRepo.findById(bServiceId);
		if (!bservice.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bservice.get().getMyBServices());
	}

	@Override
	public ResponseEntity<List<BServiceHistory>> getHistories(Long bServiceId) {
		Optional<BService> bservice = bserviceRepo.findById(bServiceId);
		if (!bservice.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bservice.get().getBServiceHistories());
	}

	@Override
	public ResponseEntity<List<BServicePrice>> getPrices(Long bServiceId) {
		Optional<BService> bservice = bserviceRepo.findById(bServiceId);
		if (!bservice.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bservice.get().getBServicePrices());
	}

	@Override
	public ResponseEntity<List<BServiceSubBanner>> getSubBanners(Long bServiceId) {
		Optional<BService> bservice = bserviceRepo.findById(bServiceId);
		if (!bservice.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bservice.get().getSubBanners());
	}

	@Override
	public ResponseEntity<List<Object[]>> getListBServiceAll() {
		Optional<List<Object[]>> BService = bserviceRepo.getListBServiceAll();
		return ResponseEntity.ok(BService.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> getListBServiceAllSearch(String textSearh) {
		Optional<List<Object[]>> BService = bserviceRepo.getListBServiceAllSearch(textSearh);
		return ResponseEntity.ok(BService.get());
	}

}
