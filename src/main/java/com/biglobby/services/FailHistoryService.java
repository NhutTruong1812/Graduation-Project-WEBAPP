package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.FailHistory;

public interface FailHistoryService {

	public ResponseEntity<FailHistory> get(Long id);

	public ResponseEntity<List<FailHistory>> get();

	public ResponseEntity<FailHistory> add(FailHistory entity);

	public ResponseEntity<FailHistory> update(Long id, FailHistory entity);

	public ResponseEntity<Integer> delete(Long id);
	
	public ResponseEntity<List<FailHistory>> getByCard(Long id);
}
