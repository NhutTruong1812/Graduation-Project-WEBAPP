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

import com.biglobby.entity.LoveCard; 
import com.biglobby.services.LoveCardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/lovecards")
public class LoveCardRestController {

	@Autowired
	private LoveCardService lcService;

	@GetMapping(params = { "user.id", "card.id" })
	public ResponseEntity<LoveCard> getUserIdAndCartId(@RequestParam("user.id") Long userId,
			@RequestParam("card.id") Long cardId) {
		return lcService.getByUserIdAndCardId(userId, cardId); 
	}

	@GetMapping
	public ResponseEntity<List<LoveCard>> get() {
		return lcService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoveCard> get(@PathVariable("id") Long id) {
		return lcService.get(id);
	}

	@PostMapping
	public ResponseEntity<LoveCard> add(@RequestBody LoveCard entity) {
		return lcService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LoveCard> update(@PathVariable("id") Long id, @RequestBody LoveCard entity) {
		return lcService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return lcService.delete(id);
	}
	
	// phần của quang dùng cho đối tác
			@GetMapping(params= {"page", "limit", "status","key","user"})
			public ResponseEntity<Page<LoveCard>> getAdminLoveCardByIdUser(
					@RequestParam("page") int Npage,
					@RequestParam("limit") int Nitem,
					@RequestParam("status") int status,
					@RequestParam("key") String textSearch, 
					@RequestParam("user") Long id) {
				Pageable pageable = PageRequest.of(Npage -1, Nitem); 
				//list page LoveCard
				if (status < 0) {
					if (status == -1) {
						return lcService.getListLoveCardAllByIdUserSearch(id, textSearch, pageable);
					} else {
						return lcService.getListLoveCardAllByIdUserSearch(id, textSearch, pageable);
					}
				} else {
					if (status == 1) {
						return lcService.getListLoveCardAllByIdUser(id, pageable);
					} else {
						return lcService.getListLoveCardAllByIdUser(id, pageable);
					}
				}
			}
			// end

}
