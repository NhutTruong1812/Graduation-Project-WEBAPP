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

import com.biglobby.entity.Category; 
import com.biglobby.entity.Product;
import com.biglobby.services.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	private ResponseEntity<List<Category>> get() {
		return categoryService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> get(@PathVariable("id") Long id) {
		return categoryService.get(id);
	}

	@PostMapping
	public ResponseEntity<Category> add(@RequestBody Category category) {
		return categoryService.add(category);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody Category category) {
		return categoryService.update(id, category);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return categoryService.delete(id);
	}

	@GetMapping("/{id}/products")
	public ResponseEntity<List<Product>> getProducts(@PathVariable("id") Long id) {
		return categoryService.getProducts(id);
	}
	
	
	/**
	 * Order Page
	 * */
	// phần của quang
	@GetMapping(params = {"page", "limit", "status", "key"})
	public ResponseEntity<Page<Category>> getAdminCategory(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, 
			@RequestParam("status") int status,
			@RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage -1, Nitem); 
		//list page Category 
		if(status < 0) { 
			if(status == -1) {   
				return categoryService.getListCategoryAllSearch(textSearch ,pageable);
			}else if(status == -2){
				return categoryService.getListCategoryStatusSearch(textSearch ,pageable);
			}else {
				return null;
			}
		}else {
			if(status == 1) {   
				return categoryService.getListCategoryAll(pageable);
			}else if(status == 2){
				return categoryService.getListCategoryStatus(pageable);
			}else {
				return null;
			}
		}
	}
	
	@GetMapping("/categoryStatus")
	private ResponseEntity<List<Category>> getListCategoryStatus3() {
		return categoryService.getListCategoryStatus3();
	}
	// end
}
