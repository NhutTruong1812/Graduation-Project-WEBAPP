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

import com.biglobby.entity.MyBService;
import com.biglobby.services.MyBServiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/mybservices")
public class MyBServiceRestController {

	@Autowired
	private MyBServiceService msService;

	@GetMapping
	public ResponseEntity<List<MyBService>> get() {
		return msService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MyBService> get(@PathVariable("id") Long id) {
		return msService.get(id);
	}

	@PostMapping
	public ResponseEntity<MyBService> add(@RequestBody MyBService entity) {
		return msService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MyBService> update(@PathVariable("id") Long id, @RequestBody MyBService entity) {
		return msService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return msService.delete(id);
	}
}
