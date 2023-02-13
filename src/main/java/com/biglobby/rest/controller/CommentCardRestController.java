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

import com.biglobby.entity.CommentCard;
import com.biglobby.entity.RepComment;
import com.biglobby.services.CommentCardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/comments")
public class CommentCardRestController {

	@Autowired
	private CommentCardService commentService;

	@GetMapping
	public ResponseEntity<List<CommentCard>> get() {
		return commentService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CommentCard> get(@PathVariable("id") Long id) {
		return commentService.get(id);
	}

	@GetMapping(params = { "cardId" })
	public ResponseEntity<List<CommentCard>> getByCardId(@PathVariable("cardId") Long cardId) {
		return commentService.getByCardId(cardId);
	}

	@PostMapping
	public ResponseEntity<CommentCard> add(@RequestBody CommentCard entity) {
		return commentService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentCard> update(@PathVariable("id") Long id, @RequestBody CommentCard entity) {
		return commentService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return commentService.delete(id);
	}

	@GetMapping("/{id}/repcomments")
	public ResponseEntity<List<RepComment>> getReplys(@PathVariable("id") Long id) {
		return commentService.getReplys(id);
	}
}
