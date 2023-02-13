package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Card;
import com.biglobby.entity.Category;
import com.biglobby.entity.Order;
import com.biglobby.entity.Status;
import com.biglobby.repository.StatusRepository;
import com.biglobby.services.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

	@Autowired
	private StatusRepository statusRepo;

	@Override
	public ResponseEntity<Status> get(Long id) {
		Optional<Status> status = statusRepo.findById(id);
		if (status.isPresent()) {
			return ResponseEntity.ok(status.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Status>> get() {
		List<Status> statuss = statusRepo.findAll();
		return ResponseEntity.ok(statuss);
	}

	@Override
	public ResponseEntity<Status> add(Status entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		Status added = statusRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Status> update(Long id, Status entity) {
		Optional<Status> exist = statusRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		Status updated = statusRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Status> exist = statusRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = statusRepo.removeByIs(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<Category>> getCategories(Long statusId) {
		Optional<Status> status = statusRepo.findById(statusId);
		if (!status.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status.get().getCategories());
	}

	@Override
	public ResponseEntity<List<Card>> getCards(Long statusId) {
		Optional<Status> status = statusRepo.findById(statusId);
		if (!status.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status.get().getCards());
	}

	@Override
	public ResponseEntity<List<Order>> getOrders(Long statusId) {
		Optional<Status> status = statusRepo.findById(statusId);
		if (!status.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(status.get().getOrders());
	}

}
