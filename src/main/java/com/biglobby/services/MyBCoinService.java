package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.MyBCoin;

public interface MyBCoinService {

	public ResponseEntity<MyBCoin> get(Long id);

	public ResponseEntity<List<MyBCoin>> get();

	public ResponseEntity<MyBCoin> add(MyBCoin entity);

	public ResponseEntity<MyBCoin> update(Long id, MyBCoin entity);

	public ResponseEntity<Integer> delete(Long id);
	
	public ResponseEntity<MyBCoin> getByUserID(Long id);
}
