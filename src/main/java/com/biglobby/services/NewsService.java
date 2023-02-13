package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.News;

public interface NewsService {

	public ResponseEntity<News> get(Long id);

	public ResponseEntity<List<News>> get();

	public ResponseEntity<News> add(News entity);

	public ResponseEntity<News> update(Long id, News entity);

	public ResponseEntity<Integer> delete(Long id);
}
