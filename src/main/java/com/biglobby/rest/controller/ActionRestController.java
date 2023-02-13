package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.Action;
import com.biglobby.entity.BCoinHistory;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.entity.ProductHistory;
import com.biglobby.services.ActionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/actions")
public class ActionRestController {

	@Autowired
	private ActionService actionService;

	@GetMapping
	public ResponseEntity<List<Action>> get() {
		return actionService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Action> get(@PathVariable("id") Long id) {
		return actionService.get(id);
	}

	@PostMapping
	public ResponseEntity<Action> add(@RequestBody Action action) {
		return actionService.add(action);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Action> update(@PathVariable("id") Long id, @RequestBody Action action) {
		return actionService.update(id, action);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return actionService.delete(id);
	}

	@GetMapping("/{id}/bcoinhistories")
	public ResponseEntity<List<BCoinHistory>> getBCoinHistories(@PathVariable("id") Long id) {
		return actionService.getBCoinHistories(id);
	}

	@GetMapping("/{id}/bservicehistories")
	public ResponseEntity<List<BServiceHistory>> getBServiceHistories(@PathVariable("id") Long id) {
		return actionService.getBServiceHistories(id);
	}

	@GetMapping("/{id}/displayfeehistory")
	public ResponseEntity<List<DisplayFeeHistory>> getDisplayFeeHistories(@PathVariable("id") Long id) {
		return actionService.getDisplayFeeHistories(id);
	}

	@GetMapping("/{id}/producthistories")
	public ResponseEntity<List<ProductHistory>> getProductHistories(@PathVariable("id") Long id) {
		return actionService.getProductHistories(id);
	}

}
