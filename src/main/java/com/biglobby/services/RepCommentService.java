package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.RepComment;

public interface RepCommentService {

	public ResponseEntity<RepComment> get(Long id);

	public ResponseEntity<List<RepComment>> get();

	public ResponseEntity<RepComment> add(RepComment entity);

	public ResponseEntity<RepComment> update(Long id, RepComment entity);

	public ResponseEntity<Integer> delete(Long id);
}
