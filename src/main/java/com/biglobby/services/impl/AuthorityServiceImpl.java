package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
 
import com.biglobby.entity.Authority;
import com.biglobby.repository.AuthorityRepository;
import com.biglobby.services.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepo;

	@Override
	public ResponseEntity<Authority> get(Long id) {
		Optional<Authority> authority = authorityRepo.findById(id);
		if (authority.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(authority.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Authority>> get() {
		List<Authority> authorities = authorityRepo.findAll();
		return ResponseEntity.ok(authorities);
	}

	@Override
	public ResponseEntity<Authority> add(Authority authority) {
		if (authority.getId() != null) {
			authority.setId(null);
		}
		Authority added = authorityRepo.save(authority);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Authority> update(Long id, Authority authority) {
		authority.setId(id);
		Authority updated = authorityRepo.save(authority);
		if (updated != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Authority> exist = authorityRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = authorityRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Authority> getByUserID(Long userId) {
		Optional<Authority> exist = authorityRepo.findByUserId(userId);
		return ResponseEntity.ok(exist.get());
	}

}
