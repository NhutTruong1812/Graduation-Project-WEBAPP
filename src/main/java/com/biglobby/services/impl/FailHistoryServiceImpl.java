package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.FailHistory;
import com.biglobby.repository.FailHistoryRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.FailHistoryService;

@Service
public class FailHistoryServiceImpl implements FailHistoryService {

	@Autowired
	private FailHistoryRepository fhRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<FailHistory> get(Long id) {
		Optional<FailHistory> failHistory = fhRepo.findById(id);
		if (failHistory.isPresent()) {
			return ResponseEntity.ok(failHistory.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<FailHistory>> get() {
		List<FailHistory> failHistories = fhRepo.findAll();
		return ResponseEntity.ok(failHistories);
	}

	@Override
	public ResponseEntity<FailHistory> add(FailHistory entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		FailHistory added = fhRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.FAIL_HISTORY + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<FailHistory> update(Long id, FailHistory entity) {
		Optional<FailHistory> exist = fhRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		FailHistory updated = fhRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.FAIL_HISTORY + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<FailHistory> exist = fhRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = fhRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.FAIL_HISTORY + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<FailHistory>> getByCard(Long id) {
		List<FailHistory> failHistories = fhRepo.getByCardId(id);
		return ResponseEntity.ok(failHistories);
	}
}
