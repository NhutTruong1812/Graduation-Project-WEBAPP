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

import com.biglobby.entity.FollowShop;
import com.biglobby.entity.MyShop;
import com.biglobby.services.MyShopService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/shops")
public class MyShopRestController {
	@Autowired
	private MyShopService msService;

	@GetMapping
	public ResponseEntity<List<MyShop>> get() {
		return msService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MyShop> get(@PathVariable("id") Long id) {
		return msService.get(id);
	}

	@PostMapping
	public ResponseEntity<MyShop> add(@RequestBody MyShop entity) {
		return msService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MyShop> update(@PathVariable("id") Long id, @RequestBody MyShop entity) {
		return msService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return msService.delete(id);
	}
 
	@GetMapping("/{id}/follows")
	public ResponseEntity<List<FollowShop>> getFollows(@PathVariable("id") Long id) {
		return msService.getFollows(id);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<MyShop> getShopByUser(@PathVariable("id") Long id) {
		return msService.getShopByUser(id); 
	}
}
