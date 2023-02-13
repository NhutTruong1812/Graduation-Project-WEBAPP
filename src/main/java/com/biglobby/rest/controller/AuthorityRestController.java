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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.Authority;
import com.biglobby.services.AuthorityService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/authorities")
public class AuthorityRestController {

	@Autowired
	private AuthorityService authorityService;

	@GetMapping
	public ResponseEntity<List<Authority>> get() {
		return authorityService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Authority> get(@PathVariable("id") Long id) {
		return authorityService.get(id);
	}

	@PostMapping
	public ResponseEntity<Authority> add(@RequestBody Authority authority) {
		return authorityService.add(authority);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Authority> update(@PathVariable("id") Long id, @RequestBody Authority authority) {
		return authorityService.update(id, authority);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return authorityService.delete(id);
	}
	
	@GetMapping(params = {"idUser"})
	public ResponseEntity<Authority> getByUserID(@RequestParam("idUser") Long idUser) {
		return authorityService.getByUserID(idUser);
	}
	
}
