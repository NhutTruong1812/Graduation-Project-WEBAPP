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

import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.services.DisplayFeeHistoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/displayfeehistories")
public class DisplayFeeHistoryRestController {

	@Autowired
	private DisplayFeeHistoryService dfhService;

	@GetMapping
	public ResponseEntity<List<DisplayFeeHistory>> get() {
		return dfhService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DisplayFeeHistory> get(@PathVariable("id") Long id) {
		return dfhService.get(id);
	}

	@PostMapping
	public ResponseEntity<DisplayFeeHistory> add(@RequestBody DisplayFeeHistory entity) {
		return dfhService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DisplayFeeHistory> update(@PathVariable("id") Long id,
			@RequestBody DisplayFeeHistory entity) {
		return dfhService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return dfhService.delete(id);
	}
}
