package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.MyBCoin;
import com.biglobby.repository.MyBCoinRepository;
import com.biglobby.services.MyBCoinService;

@Service
public class MyBCoinServiceImpl implements MyBCoinService {

	@Autowired
	private MyBCoinRepository mbcRepo;

	@Override
	public ResponseEntity<MyBCoin> get(Long id) {
		Optional<MyBCoin> myBCoin = mbcRepo.findById(id);
		if (myBCoin.isPresent()) {
			return ResponseEntity.ok(myBCoin.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<MyBCoin>> get() {
		List<MyBCoin> myBCoins = mbcRepo.findAll();
		return ResponseEntity.ok(myBCoins);
	}

	@Override
	public ResponseEntity<MyBCoin> add(MyBCoin entity) {
		MyBCoin added = mbcRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<MyBCoin> update(Long id, MyBCoin entity) {
		Optional<MyBCoin> exist = mbcRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		MyBCoin updated = mbcRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<MyBCoin> exist = mbcRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = mbcRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<MyBCoin> getByUserID(Long idUser) {
		Optional<MyBCoin> exist = mbcRepo.findByUserId(idUser);
		return ResponseEntity.ok(exist.get());
	}

}
