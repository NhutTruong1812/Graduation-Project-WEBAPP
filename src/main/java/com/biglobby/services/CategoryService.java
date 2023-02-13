package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Category; 
import com.biglobby.entity.Product;

public interface CategoryService {

	public ResponseEntity<Category> get(Long id);

	public ResponseEntity<List<Category>> get();

	public ResponseEntity<Category> add(Category category);

	public ResponseEntity<Category> update(Long id, Category category);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<Product>> getProducts(Long categoryId);
	
	
	// phần của quang
	//ALL  
	public ResponseEntity<Page<Category>> getListCategoryAll(Pageable pageable);
	
	public ResponseEntity<Page<Category>> getListCategoryStatus(Pageable pageable);
		
	//ALL SEARCH 
	public ResponseEntity<Page<Category>> getListCategoryAllSearch(String textSearh, Pageable pageable);
	
	//ALL  where status
	public ResponseEntity<Page<Category>> getListCategoryAllWhereStatus(Long status ,Pageable pageable);
		
	//ALL where status SEARCH 
	public ResponseEntity<Page<Category>> getListCategoryAllWhereStatusSearch(Long status , String textSearh, Pageable pageable);
	
	//ALL  where status nopage
	public ResponseEntity<List<Category>> getListCategoryAllWhereStatus(Long status);
		
	//ALL where status SEARCH  nopage
	public ResponseEntity<List<Category>> getListCategoryAllWhereStatusSearch(Long status , String textSearh);
	
	//ALL  where
	public ResponseEntity<Page<Category>> getListCategoryAllWhereBlockAndStatus(boolean block, Long status,Pageable pageable);
		
	//ALL where SEARCH 
	public ResponseEntity<Page<Category>> getListCategoryAllWhereBlockAndStatusSearch(boolean block, Long status, String textSearh, Pageable pageable);
	//--->
	public ResponseEntity<Page<Category>> getListCategoryStatusSearch(String textSearh, Pageable pageable);
	
	public ResponseEntity<List<Category>> getListCategoryStatus3();
	// end
}
