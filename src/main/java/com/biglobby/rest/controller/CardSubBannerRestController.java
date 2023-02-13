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

import com.biglobby.entity.CardSubBanner;
import com.biglobby.services.CardSubBannerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cardsubbanners")
public class CardSubBannerRestController {

	@Autowired
	private CardSubBannerService csService;

	@GetMapping
	public ResponseEntity<List<CardSubBanner>> get() {
		return csService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CardSubBanner> get(@PathVariable("id") Long id) {
		return csService.get(id);
	}

	@PostMapping
	public ResponseEntity<CardSubBanner> add(@RequestBody CardSubBanner entity) {
		return csService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CardSubBanner> update(@PathVariable("id") Long id, @RequestBody CardSubBanner entity) {
		return csService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return csService.delete(id);
	}

	@DeleteMapping("/{idCard}/cardsubbanners")
	public ResponseEntity<Integer> deleteByIdCard(@PathVariable("idCard") Long idCard) {
		return csService.deleteByIdCard(idCard);
	}

	@GetMapping("/{cardId}/cardsubbanners")
	public ResponseEntity<List<CardSubBanner>> getCardSubBanner(@PathVariable("cardId") Long id) {
		return csService.getCardsubbannerbyCardId(id);
	}

}
