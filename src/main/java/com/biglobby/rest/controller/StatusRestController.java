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

import com.biglobby.entity.Card;
import com.biglobby.entity.Category;
import com.biglobby.entity.Order;
import com.biglobby.entity.Status;
import com.biglobby.services.StatusService;

@CrossOrigin
@RestController
@RequestMapping("/api/status")
public class StatusRestController {

	@Autowired
	private StatusService statusService;

	@GetMapping
	public ResponseEntity<List<Status>> get() {
		return statusService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Status> get(@PathVariable("id") Long id) {
		return statusService.get(id);
	}

	@PostMapping
	public ResponseEntity<Status> add(@RequestBody Status entity) {
		return statusService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Status> update(@PathVariable("id") Long id, @RequestBody Status entity) {
		return statusService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return statusService.delete(id);
	}

	@GetMapping("/{id}/categories")
	public ResponseEntity<List<Category>> getCategories(@PathVariable("id") Long id) {
		return statusService.getCategories(id);
	}

	@GetMapping("/{id}/cards")
	public ResponseEntity<List<Card>> getCards(@PathVariable("id") Long id) {
		return statusService.getCards(id);
	}

	@GetMapping("/{id}/orders")
	public ResponseEntity<List<Order>> getOrders(@PathVariable("id") Long id) {
		return statusService.getOrders(id);
	}
}
