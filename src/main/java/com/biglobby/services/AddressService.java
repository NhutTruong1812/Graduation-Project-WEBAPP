package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Address;

public interface AddressService {

	public ResponseEntity<Address> get(Long id);

	public ResponseEntity<List<Address>> get();

	public ResponseEntity<Address> add(Address address);

	public ResponseEntity<Address> update(Long id, Address address);

	public ResponseEntity<Integer> delete(Long id);
}
