package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Category;
import com.biglobby.entity.Product;
import com.biglobby.repository.CategoryRepository;
import com.biglobby.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public ResponseEntity<Category> get(Long id) {
		Optional<Category> category = categoryRepo.findById(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(category.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Category>> get() {
		List<Category> categories = categoryRepo.findAll();
		return ResponseEntity.ok(categories);
	}

	@Override
	public ResponseEntity<Category> add(Category category) {
		if (category.getId() != null) {
			category.setId(null);
		}
		Category added = categoryRepo.save(category);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Category> update(Long id, Category category) {
		Optional<Category> exist = categoryRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		System.err.println("vô đây");
		category.setId(id);
		Category updated = categoryRepo.save(category);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Category> exist = categoryRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = categoryRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<Product>> getProducts(Long categoryId) {
		Optional<Category> category = categoryRepo.findById(categoryId); 
		if (!category.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(category.get().getProducts());
	}

	// phần của quang
	@Override
	public ResponseEntity<Page<Category>> getListCategoryAll(Pageable pageable) {
		Optional<Page<Category>> Category = categoryRepo.getListCategoryAll(pageable);
		return ResponseEntity.ok(Category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryAllSearch(String textSearh, Pageable pageable) {
		Optional<Page<Category>> category = categoryRepo.getListCategoryAllSearch(textSearh, pageable);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryAllWhereBlockAndStatus(boolean block, Long status,
			Pageable pageable) {
		Optional<Page<Category>> category = categoryRepo.getListCategoryAllWhereBlockAndStatus(block, status, pageable);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryAllWhereBlockAndStatusSearch(boolean block, Long status,
			String textSearh, Pageable pageable) {
		Optional<Page<Category>> category = categoryRepo.getListCategoryAllWhereBlockAndStatusSearch(block, status,
				textSearh, pageable);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryAllWhereStatus(Long status, Pageable pageable) {
		Optional<Page<Category>> category = categoryRepo.getListCategoryAllWhereStatus(status, pageable);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryAllWhereStatusSearch(Long status, String textSearh,
			Pageable pageable) {
		Optional<Page<Category>> category = categoryRepo.getListCategoryAllWhereStatusSearch(status, textSearh,
				pageable);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<List<Category>> getListCategoryAllWhereStatus(Long status) {
		Optional<List<Category>> category = categoryRepo.getListCategoryAllWhereStatus(status);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<List<Category>> getListCategoryAllWhereStatusSearch(Long status, String textSearh) {
		Optional<List<Category>> category = categoryRepo.getListCategoryAllWhereStatusSearch(status, textSearh);
		return ResponseEntity.ok(category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryStatus(Pageable pageable) {
		Optional<Page<Category>> Category = categoryRepo.getListCategoryStatus(pageable);
		return ResponseEntity.ok(Category.get());
	}

	@Override
	public ResponseEntity<Page<Category>> getListCategoryStatusSearch(String textSearh, Pageable pageable) {
		Optional<Page<Category>> Category = categoryRepo.getListCategoryStatusSearch(textSearh, pageable);
		return ResponseEntity.ok(Category.get());
	}
	// end

	@Override
	public ResponseEntity<List<Category>> getListCategoryStatus3() {
		List<Category> categories = categoryRepo.getListCategoryStatus3();
		return ResponseEntity.ok(categories);
	}

}
