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

import com.biglobby.entity.BServicePrice; 
import com.biglobby.services.BServicePriceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bserviceprices")
public class BServicePriceRestController {
	@Autowired
	private BServicePriceService bspService;

	@GetMapping()
	public ResponseEntity<List<BServicePrice>> get() {
		return bspService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BServicePrice> get(@PathVariable("id") Long id) {
		return bspService.get(id);
	}

	@PostMapping
	public ResponseEntity<BServicePrice> add(@RequestBody BServicePrice enity) {
		return bspService.add(enity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BServicePrice> update(@PathVariable("id") Long id, @RequestBody BServicePrice entity) {
		return bspService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return bspService.delete(id);
	}
	
	@GetMapping("/bservice/{id}")
	public ResponseEntity<List<BServicePrice>> findByBserviceId(@PathVariable("id") Long id) {
		return bspService.findByBserviceId(id);
	}

}
