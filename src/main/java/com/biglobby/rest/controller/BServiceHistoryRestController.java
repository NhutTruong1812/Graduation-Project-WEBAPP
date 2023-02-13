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

import com.biglobby.entity.BServiceHistory; 
import com.biglobby.services.BServiceHistoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bservicehistories")
public class BServiceHistoryRestController {

	@Autowired
	private BServiceHistoryService bshService;

	@GetMapping
	public ResponseEntity<List<BServiceHistory>> get() {
		return bshService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BServiceHistory> get(@PathVariable("id") Long id) {
		return bshService.get(id);
	}

	@PostMapping
	public ResponseEntity<BServiceHistory> add(@RequestBody BServiceHistory entity) {
		return bshService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BServiceHistory> update(@PathVariable("id") Long id, @RequestBody BServiceHistory entity) {
		return bshService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return bshService.delete(id);
	}
	
	// phần của quang
		@GetMapping(params = {"page", "limit", "status", "key", "user"})
		public ResponseEntity<Page<BServiceHistory>> getAdminBServiceHistory(
				@RequestParam("page") int Npage,
				@RequestParam("limit") int Nitem 
				,@RequestParam("status") int status,
				@RequestParam("key") String textSearch, 
				@RequestParam("user") Long id) {
			Pageable pageable = PageRequest.of(Npage -1, Nitem); 
			
			//list page BServiceHistory
			if(status < 0) { 
				if(status == -1) {   
					return bshService.getListBServiceHistoryAllSearch(textSearch ,pageable);
				}else if(status == -2) {
					return bshService.getListBServiceHistoryAllByIdSearch(id, textSearch ,pageable);
				}else {
					return bshService.getListBServiceHistoryAllSearch(textSearch ,pageable);
				}
			}else {
				if(status == 1) {   
					return bshService.getListBServiceHistoryAll(pageable);
				}else if(status == 2){
					return bshService.getListBServiceHistoryAllById(id, pageable);
				}else {
					return bshService.getListBServiceHistoryAll(pageable);
				}
			}
		}
		// end
}
