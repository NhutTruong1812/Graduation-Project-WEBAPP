package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Authority;

public interface AuthorityService {

	public ResponseEntity<Authority> get(Long id);

	public ResponseEntity<List<Authority>> get();

	public ResponseEntity<Authority> add(Authority authority);

	public ResponseEntity<Authority> update(Long id, Authority authority);

	public ResponseEntity<Integer> delete(Long id);
	
	public ResponseEntity<Authority> getByUserID(Long userId);
}
