package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Action;
import com.biglobby.entity.BCoinHistory;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.entity.ProductHistory;
import com.biglobby.repository.ActionRepository;
import com.biglobby.services.ActionService;

@Service
public class ActionServiceImpl implements ActionService {

	@Autowired
	private ActionRepository actionRepo;

	@Override
	public ResponseEntity<Action> get(Long id) {
		Optional<Action> action = actionRepo.findById(id);
		if (action.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(action.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Action>> get() {
		List<Action> actions = actionRepo.findAll();
		return ResponseEntity.ok(actions);
	}

	@Override
	public ResponseEntity<Action> add(Action action) {
		if (action.getId() != null) {
			action.setId(null);
		}
		Action added = actionRepo.save(action);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Action> update(Long id, Action action) {
		Optional<Action> exist = actionRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		action.setId(id);
		Action updated = actionRepo.save(action);
		if (updated != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updated);
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Action> exist = actionRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = actionRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<BCoinHistory>> getBCoinHistories(Long actionId) {
		Optional<Action> action = actionRepo.findById(actionId);
		if (!action.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(action.get().getBCoinHistories());
	}

	@Override
	public ResponseEntity<List<BServiceHistory>> getBServiceHistories(Long actionId) {
		Optional<Action> action = actionRepo.findById(actionId);
		if (!action.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(action.get().getBServiceHistories());
	}

	@Override
	public ResponseEntity<List<DisplayFeeHistory>> getDisplayFeeHistories(Long actionId) {
		Optional<Action> action = actionRepo.findById(actionId);
		if (!action.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(action.get().getDisplayFeeHistories());
	}

	@Override
	public ResponseEntity<List<ProductHistory>> getProductHistories(Long actionId) {
		Optional<Action> action = actionRepo.findById(actionId);
		if (!action.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(action.get().getProductHistories());
	}

}
