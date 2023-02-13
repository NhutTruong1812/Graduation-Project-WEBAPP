package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.BServicePrice;

public interface BServicePriceService {

	public ResponseEntity<BServicePrice> get(Long id);

	public ResponseEntity<List<BServicePrice>> get();

	public ResponseEntity<BServicePrice> add(BServicePrice bservicePrice);

	public ResponseEntity<BServicePrice> update(Long id, BServicePrice bservicePrice);

	public ResponseEntity<Integer> delete(Long id);
	
	public ResponseEntity<List<BServicePrice>> findByBserviceId(Long id);

}
