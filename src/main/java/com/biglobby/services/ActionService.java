package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Action;
import com.biglobby.entity.BCoinHistory;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.entity.ProductHistory;

public interface ActionService {

	public ResponseEntity<Action> get(Long id);

	public ResponseEntity<List<Action>> get();

	public ResponseEntity<Action> add(Action action);

	public ResponseEntity<Action> update(Long id, Action action);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<BCoinHistory>> getBCoinHistories(Long actionId);

	public ResponseEntity<List<BServiceHistory>> getBServiceHistories(Long actionId);

	public ResponseEntity<List<DisplayFeeHistory>> getDisplayFeeHistories(Long actionId);

	public ResponseEntity<List<ProductHistory>> getProductHistories(Long actionId);
}
