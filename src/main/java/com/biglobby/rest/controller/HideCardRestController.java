package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.biglobby.entity.HideCard;
import com.biglobby.services.HideCardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hidecards")
public class HideCardRestController {

	@Autowired
	private HideCardService hdService;

	@GetMapping
	public ResponseEntity<List<HideCard>> get() {
		return hdService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<HideCard> get(@PathVariable("id") Long id) {
		return hdService.get(id);
	}

	@PostMapping
	public ResponseEntity<HideCard> add(@RequestBody HideCard entity) {
		return hdService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HideCard> update(@PathVariable("id") Long id, @RequestBody HideCard entity) {
		return hdService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return hdService.delete(id);
	}

	@GetMapping(params = { "idCard", "idUser" })
	public ResponseEntity<HideCard> findByIDCardANDIDUser(@RequestParam("idCard") Long idCard,
			@RequestParam("idUser") Long idUser) {
		return hdService.findByIDCardANDIDUser(idCard, idUser);
	}

	@GetMapping(params = { "page", "limit", "status", "key", "idUser" })
	public ResponseEntity<Page<HideCard>> getListProductFromHideCardWithUser(@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, @RequestParam("status") int status, @RequestParam("idUser") Long idUser,
			@RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		return hdService.getListProductFromHideCardWithUser(idUser, pageable);
	}
	
	@GetMapping(params = { "idUser", "loadAllHideCardWithUser" })
	public ResponseEntity<List<HideCard>> loadAllHideCardWithUser(@RequestParam("idUser") Long idUser,
			@RequestParam("loadAllHideCardWithUser") int loadAllHideCardWithUser) {
		return hdService.loadAllHideCardWithUser(idUser);
	}
}
