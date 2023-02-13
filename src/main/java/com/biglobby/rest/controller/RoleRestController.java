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

import com.biglobby.entity.Authority;
import com.biglobby.entity.Role;
import com.biglobby.services.RoleService;

@CrossOrigin
@RestController
@RequestMapping("/api/roles")
public class RoleRestController {
	@Autowired
	private RoleService roleService;

	@GetMapping
	public ResponseEntity<List<Role>> get() {
		return roleService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Role> get(@PathVariable("id") Long id) {
		return roleService.get(id);
	}

	@PostMapping
	public ResponseEntity<Role> add(@RequestBody Role entity) {
		return roleService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Role> update(@PathVariable("id") Long id, @RequestBody Role entity) {
		return roleService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return roleService.delete(id);
	}

	@GetMapping("/{id}/authorities")
	public ResponseEntity<List<Authority>> getAuthorities(@PathVariable("id") Long id) {
		return roleService.getAuthorities(id);
	}
}
