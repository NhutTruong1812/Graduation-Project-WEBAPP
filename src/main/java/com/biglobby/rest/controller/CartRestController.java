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

import com.biglobby.entity.Cart;
import com.biglobby.services.CartService;  

@CrossOrigin
@RestController
@RequestMapping("/api/carts")
public class CartRestController {

	@Autowired
	private CartService cartService;

	@GetMapping
	public ResponseEntity<List<Cart>> get() {
		return cartService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cart> get(@PathVariable("id") Long id) {
		return cartService.get(id);
	}

	@PostMapping
	public ResponseEntity<Cart> add(@RequestBody Cart cart) {
		return cartService.add(cart);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cart> update(@PathVariable("id") Long id, @RequestBody Integer quantity) {
		return cartService.update(id, quantity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return cartService.deleted(id);
	}

	@GetMapping("/{id}/totals")
	public ResponseEntity<Double> getTotals(@PathVariable("id") Long userId) {
		return cartService.getTotal(userId);
	}

}
