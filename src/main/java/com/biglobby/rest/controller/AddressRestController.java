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

import com.biglobby.entity.Address;
import com.biglobby.services.AddressService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/addresses")
public class AddressRestController {

	@Autowired
	private AddressService addressService;

	@GetMapping
	public ResponseEntity<List<Address>> get() {
		return addressService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Address> get(@PathVariable("id") Long id) {
		return addressService.get(id);
	}

	@PostMapping
	public ResponseEntity<Address> add(@RequestBody Address address) { 
		return addressService.add(address);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Address> update(@PathVariable("id") Long id, @RequestBody Address address) {
		return addressService.update(id, address);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return addressService.delete(id);
	}
}
