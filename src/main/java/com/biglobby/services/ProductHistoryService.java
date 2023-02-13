package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.ProductHistory;

public interface ProductHistoryService {

	public ResponseEntity<ProductHistory> get(Long id);

	public ResponseEntity<List<ProductHistory>> get();

	public ResponseEntity<ProductHistory> add(ProductHistory entity);

	public ResponseEntity<ProductHistory> update(Long id, ProductHistory entity);

	public ResponseEntity<Integer> delete(Long id);

}
