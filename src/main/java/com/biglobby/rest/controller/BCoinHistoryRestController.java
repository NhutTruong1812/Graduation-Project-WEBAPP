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

import com.biglobby.entity.BCoinHistory;
import com.biglobby.services.BCoinHistoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bcoinhistories")
public class BCoinHistoryRestController {

	@Autowired
	private BCoinHistoryService bchService;

	@GetMapping
	public ResponseEntity<List<BCoinHistory>> get() {
		return bchService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BCoinHistory> get(@PathVariable("id") Long id) {
		return bchService.get(id);
	}

	@PostMapping
	public ResponseEntity<BCoinHistory> add(@RequestBody BCoinHistory entity) {
		return bchService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BCoinHistory> update(@PathVariable("id") Long id, @RequestBody BCoinHistory entity) {
		return bchService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return bchService.delete(id);
	}
	
	// phần của quang dùng cho lịch sử nập tiền
	@GetMapping(params = {"page", "limit", "status", "key"})
	public ResponseEntity<Page<BCoinHistory>> getAdminBCoinHistory(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, 
			@RequestParam("status") int status,
			@RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page BCoinHistory
		if (status < 0) {
			if (status == -1) {
				return bchService.getListBCoinHistoryAllSearch(textSearch, pageable);
			} else {
				return bchService.getListBCoinHistoryAllSearch(textSearch, pageable);
			}
		} else {
			if (status == 1) {
				return bchService.getListBCoinHistoryAll(pageable);
			} else {
				return bchService.getListBCoinHistoryAll(pageable);
			}
		}
	}
	// end
}
