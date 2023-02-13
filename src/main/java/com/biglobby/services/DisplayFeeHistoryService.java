package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.DisplayFeeHistory;

public interface DisplayFeeHistoryService {

	public ResponseEntity<DisplayFeeHistory> get(Long id);

	public ResponseEntity<List<DisplayFeeHistory>> get();

	public ResponseEntity<DisplayFeeHistory> add(DisplayFeeHistory entity);

	public ResponseEntity<DisplayFeeHistory> update(Long id, DisplayFeeHistory entity);

	public ResponseEntity<Integer> delete(Long id);
}
