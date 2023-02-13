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

import com.biglobby.entity.Problem;
import com.biglobby.entity.ReportCard;
import com.biglobby.services.ProblemService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/problems")
public class ProblemRestController {

	@Autowired
	private ProblemService pbService;

	@GetMapping
	public ResponseEntity<List<Problem>> get() {
		return pbService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Problem> get(@PathVariable("id") Long id) {
		return pbService.get(id);
	}

	@PostMapping
	public ResponseEntity<Problem> add(@RequestBody Problem entity) {
		return pbService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Problem> update(@PathVariable("id") Long id, @RequestBody Problem entity) {
		return pbService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return pbService.delete(id);
	}

	@GetMapping("/{id}/reports")
	public ResponseEntity<List<ReportCard>> getReports(@PathVariable("id") Long id) {
		return pbService.getReportCards(id);
	}
}
