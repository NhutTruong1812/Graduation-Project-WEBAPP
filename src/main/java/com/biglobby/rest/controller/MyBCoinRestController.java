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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.MyBCoin;
import com.biglobby.services.MyBCoinService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/mybcoins")
public class MyBCoinRestController {

	@Autowired
	private MyBCoinService mybcoinService;

	@GetMapping
	public ResponseEntity<List<MyBCoin>> get() {
		return mybcoinService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MyBCoin> get(@PathVariable("id") Long id) {
		return mybcoinService.get(id);
	}

	@PostMapping
	public ResponseEntity<MyBCoin> add(@RequestBody MyBCoin entity) {
		return mybcoinService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MyBCoin> update(@PathVariable("id") Long id, @RequestBody MyBCoin entity) {
		return mybcoinService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return mybcoinService.delete(id);
	}
	
	@GetMapping(params = {"idUser"})
	public ResponseEntity<MyBCoin> getByUserID(@RequestParam("idUser") Long idUser) {
		return mybcoinService.getByUserID(idUser);
	}
}
