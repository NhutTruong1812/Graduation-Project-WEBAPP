package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.MyBService;

public interface MyBServiceService {

	public ResponseEntity<MyBService> get(Long id);

	public ResponseEntity<List<MyBService>> get();

	public ResponseEntity<MyBService> add(MyBService entity);

	public ResponseEntity<MyBService> update(Long id, MyBService entity);

	public ResponseEntity<Integer> delete(Long id);
}
