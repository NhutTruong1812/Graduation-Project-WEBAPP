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

import com.biglobby.entity.BServiceSubBanner;
import com.biglobby.services.BServiceSubBannerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bservicesubbanners")
public class BServiceSubBannerRestController {

	@Autowired
	private BServiceSubBannerService bsService;

	@GetMapping
	public ResponseEntity<List<BServiceSubBanner>> get() {
		return bsService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BServiceSubBanner> get(@PathVariable("id") Long id) {
		return bsService.get(id);
	}

	@PostMapping
	public ResponseEntity<BServiceSubBanner> add(@RequestBody BServiceSubBanner entity) {
		return bsService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BServiceSubBanner> update(@PathVariable("id") Long id,
			@RequestBody BServiceSubBanner entity) {
		return bsService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return bsService.delete(id);
	}
}
