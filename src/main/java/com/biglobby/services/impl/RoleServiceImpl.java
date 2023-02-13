package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Authority;
import com.biglobby.entity.Role;
import com.biglobby.repository.RoleRepository;
import com.biglobby.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public ResponseEntity<Role> get(Long id) {
		Optional<Role> role = roleRepo.findById(id);
		if (role.isPresent()) {
			return ResponseEntity.ok(role.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Role>> get() {
		List<Role> roles = roleRepo.findAll();
		return ResponseEntity.ok(roles);
	}

	@Override
	public ResponseEntity<Role> add(Role entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		Role added = roleRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Role> update(Long id, Role entity) {
		Optional<Role> exist = roleRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		Role updated = roleRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Role> exist = roleRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = roleRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<Authority>> getAuthorities(Long roleId) {
		Optional<Role> role = roleRepo.findById(roleId);
		if (!role.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(role.get().getAuthorities());
	}

}
