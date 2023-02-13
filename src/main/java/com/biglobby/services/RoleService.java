package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Authority;
import com.biglobby.entity.Role;

public interface RoleService {

	public ResponseEntity<Role> get(Long id);

	public ResponseEntity<List<Role>> get();

	public ResponseEntity<Role> add(Role entity);

	public ResponseEntity<Role> update(Long id, Role entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<Authority>> getAuthorities(Long roleId);
}
