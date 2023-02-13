package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Address;
import com.biglobby.repository.AddressRepository;
import com.biglobby.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public ResponseEntity<Address> get(Long id) {
		Optional<Address> address = addressRepo.findById(id);
		if (address.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(address.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Address>> get() {
		List<Address> addresses = addressRepo.findAll();
		return ResponseEntity.ok(addresses);
	}

	@Override
	public ResponseEntity<Address> add(Address address) {
		Address added = addressRepo.save(address);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Address> update(Long id, Address address) {
		Optional<Address> exist = addressRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		address.setId(id);
		Address updated = addressRepo.save(address);
		if (updated != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Address> exist = addressRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = addressRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
