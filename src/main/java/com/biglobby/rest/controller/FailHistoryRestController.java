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

import com.biglobby.entity.FailHistory;
import com.biglobby.services.FailHistoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/failhistories")
public class FailHistoryRestController {

	@Autowired
	private FailHistoryService fhService;

	@GetMapping
	public ResponseEntity<List<FailHistory>> get() {
		return fhService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<FailHistory> get(@PathVariable("id") Long id) {
		return fhService.get(id);
	}

	@PostMapping
	public ResponseEntity<FailHistory> add(@RequestBody FailHistory entity) {
		return fhService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FailHistory> update(@PathVariable("id") Long id, @RequestBody FailHistory entity) {
		return fhService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return fhService.delete(id);
	}
	
	@GetMapping("/card/{id}")
	public ResponseEntity<List<FailHistory>> getByCard(@PathVariable("id") Long id) {
		return ResponseEntity.ok(fhService.getByCard(id).getBody());
	}
}
