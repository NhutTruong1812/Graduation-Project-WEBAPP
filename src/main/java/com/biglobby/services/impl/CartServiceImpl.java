package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Cart;
import com.biglobby.repository.CartRepository;
import com.biglobby.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;

	@Override
	public ResponseEntity<Cart> get(Long id) {
		Optional<Cart> cart = cartRepo.findById(id);
		if (!cart.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cart.get());
	}

	@Override
	public ResponseEntity<List<Cart>> get() {
		List<Cart> carts = cartRepo.findAll();
		return ResponseEntity.ok(carts);
	}

	@Override
	public ResponseEntity<List<Cart>> getByUserId(Long userId) {
		List<Cart> carts = cartRepo.findByUserId(userId);
		return ResponseEntity.ok(carts);
	}

	@Override
	public ResponseEntity<Cart> add(Cart cart) {
		if (cart.getId() != null) {
			cart.setId(null);
		}
		Optional<Cart> exist = cartRepo.findByUserIdAndProductId(cart.getUser().getId(),
				cart.getProduct().getCard().getId());
		if (!exist.isPresent()) {
			Cart added = cartRepo.save(cart);
			if (added != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(added);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return update(exist.get().getId(), cart.getQuantity());
	}

	@Override
	public ResponseEntity<Cart> update(Long id, Integer quantity) {
		Optional<Cart> exist = cartRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Cart entity = exist.get();
		entity.setQuantity(quantity);
		Cart updated = cartRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleted(Long id) {
		Optional<Cart> exist = cartRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = cartRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Double> getTotal(Long userId) {
		Double total = 0.0;
		List<Cart> carts = cartRepo.findByUserId(userId);
		if (!carts.isEmpty()) {
			for (int i = 0; i < carts.size(); i++) {
				Cart cart = carts.get(i);
				int quantity = cart.getQuantity();
				Double price = cart.getProduct().getPrice();
				total += (quantity * price);
			}
		}
		return ResponseEntity.ok(total);
	}

	@Override
	public ResponseEntity<Cart> deletedCartById(Long id) {
		Optional<Cart> existOptional = cartRepo.findById(id);
		if (!existOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		cartRepo.deleteById(id);
		return ResponseEntity.ok().build();
	}

}
