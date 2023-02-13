package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.DisplayFee;
import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.services.DisplayFeeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/displayfees")
public class DisplayFeeRestController {

	@Autowired
	private DisplayFeeService dfService;

	@GetMapping
	public ResponseEntity<List<DisplayFee>> get() {
		return dfService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DisplayFee> get(@PathVariable("id") Long id) {
		return dfService.get(id);
	}

	@GetMapping(params = { "page", "limit" })
	public ResponseEntity<Page<DisplayFee>> getPage(@RequestParam("page") Integer pagenum,
			@RequestParam("limit") Integer limit) {
		return dfService.getPage(pagenum, limit);
	}

	@GetMapping(params = { "price" })
	public ResponseEntity<DisplayFee> getByPriceRange(@RequestParam("price") Double price) {
		return dfService.findByPriceInRange(price);
	}

	@PostMapping
	public ResponseEntity<DisplayFee> add(@RequestBody DisplayFee entity) {
		return dfService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DisplayFee> update(@PathVariable("id") Long id, @RequestBody DisplayFee entity) {
		return dfService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return dfService.delete(id);
	}

	@GetMapping("/{id}/histories")
	public ResponseEntity<List<DisplayFeeHistory>> getHistories(@PathVariable("id") Long id) {
		return dfService.getHistories(id);
	}
}
