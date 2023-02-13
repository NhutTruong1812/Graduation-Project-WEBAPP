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

import com.biglobby.entity.RepComment;
import com.biglobby.services.RepCommentService;

@CrossOrigin
@RestController
@RequestMapping("/api/repcomments")
public class RepCommentRestController {
	@Autowired
	private RepCommentService replyService;

	@GetMapping
	public ResponseEntity<List<RepComment>> get() {
		return replyService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<RepComment> get(@PathVariable("id") Long id) {
		return replyService.get(id);
	}

	@PostMapping
	public ResponseEntity<RepComment> add(@RequestBody RepComment entity) {
		return replyService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RepComment> update(@PathVariable("id") Long id, @RequestBody RepComment entity) {
		return replyService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return replyService.delete(id);
	}
}
