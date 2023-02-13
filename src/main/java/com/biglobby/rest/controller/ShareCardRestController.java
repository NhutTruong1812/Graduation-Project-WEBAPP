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

import com.biglobby.entity.ShareCard;
import com.biglobby.services.ShareCardService;

@CrossOrigin
@RestController
@RequestMapping("/api/shares")
public class ShareCardRestController {
	
	@Autowired
	private ShareCardService shareService;

	@GetMapping
	public ResponseEntity<List<ShareCard>> get() {
		return shareService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShareCard> get(@PathVariable("id") Long id) {
		return shareService.get(id);
	}

	@PostMapping
	public ResponseEntity<ShareCard> add(@RequestBody ShareCard entity) {
		return shareService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ShareCard> update(@PathVariable("id") Long id, @RequestBody ShareCard entity) {
		return shareService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return shareService.delete(id);
	}
}
