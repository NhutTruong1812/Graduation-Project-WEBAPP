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

import com.biglobby.entity.ProductHistory;
import com.biglobby.services.ProductHistoryService;

@CrossOrigin
@RestController
@RequestMapping("/api/producthistories")
public class ProductHistoryRestController {

	@Autowired
	private ProductHistoryService phService;

	@GetMapping
	public ResponseEntity<List<ProductHistory>> get() {
		return phService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductHistory> get(@PathVariable("id") Long id) {
		return phService.get(id);
	}

	@PostMapping
	public ResponseEntity<ProductHistory> add(@RequestBody ProductHistory entity) {
		return phService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductHistory> update(@PathVariable("id") Long id, @RequestBody ProductHistory entity) {
		return phService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return phService.delete(id);
	}

}
