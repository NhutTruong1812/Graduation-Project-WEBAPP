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

import com.biglobby.entity.FollowShop; 
import com.biglobby.services.FollowShopService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/followshops")
public class FollowShopRestController {

	@Autowired
	private FollowShopService flsService;

	@GetMapping
	public ResponseEntity<List<FollowShop>> get() {
		return flsService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<FollowShop> get(@PathVariable("id") Long id) {
		return flsService.get(id);
	}

	@PostMapping
	public ResponseEntity<FollowShop> add(@RequestBody FollowShop entity) {
		return flsService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FollowShop> update(@PathVariable("id") Long id, @RequestBody FollowShop entity) {
		return flsService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return flsService.delete(id);
	}
	
	//xem sản phẩm của hàng khác
	@GetMapping(params= {"page", "limit", "pagewhere","user"})
	public ResponseEntity<Page<FollowShop>> getPageFollowShopUserALl(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem ,   
			@RequestParam("pagewhere") int pagewhere,  
			@RequestParam("user")Long user) {
		Pageable pageable = PageRequest.of(Npage -1, Nitem);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");
		System.err.println("NPage: " + Npage);
		System.err.println("NItem: " + Nitem); 
		System.err.println("pagewhere: " + pagewhere);  
		System.err.println("user: " + user);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");
		
		if(pagewhere < 0) { 
			return flsService.getListFollowALL( user, pageable);
		}else {
			if(pagewhere == 1) {   
				return flsService.getListFollowALL( user, pageable);
			}else if(pagewhere == 2) {
				return flsService.getListFollowByShop( user, pageable);
			}   else {
				return flsService.getListFollowALL(user, pageable);
			}
		} 
	}
	
}
