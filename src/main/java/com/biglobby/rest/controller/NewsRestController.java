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

import com.biglobby.entity.News;
import com.biglobby.services.NewsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/news")
public class NewsRestController {
	@Autowired
	private NewsService newsService;

	@GetMapping
	public ResponseEntity<List<News>> get() {
		return newsService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<News> get(@PathVariable("id") Long id) {
		return newsService.get(id);
	}

	@PostMapping
	public ResponseEntity<News> add(@RequestBody News entity) {
		return newsService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<News> update(@PathVariable("id") Long id, @RequestBody News entity) {
		return newsService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return newsService.delete(id);
	}
}
