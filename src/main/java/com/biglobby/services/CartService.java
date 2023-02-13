package com.biglobby.services;

import java.util.List;
 
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Cart; 

public interface CartService {

	public ResponseEntity<Cart> get(Long id);
	
	public ResponseEntity<List<Cart>> get();

	public ResponseEntity<List<Cart>> getByUserId(Long userId);
	
	public ResponseEntity<Cart> add(Cart cart);
	
	public ResponseEntity<Cart> update(Long id, Integer quantity);
	
	public ResponseEntity<Integer> deleted(Long id);
	
	public ResponseEntity<Double> getTotal(Long userId);
	
	public ResponseEntity<Cart> deletedCartById(Long id);
}
