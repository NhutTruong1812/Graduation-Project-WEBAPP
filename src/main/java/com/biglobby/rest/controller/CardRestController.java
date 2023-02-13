package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

import com.biglobby.entity.Card;
import com.biglobby.entity.CardSubBanner;
import com.biglobby.entity.CommentCard;
import com.biglobby.entity.FailHistory;
import com.biglobby.entity.HideCard;
import com.biglobby.entity.LoveCard;
import com.biglobby.entity.Post;
import com.biglobby.entity.Product;
import com.biglobby.entity.ReportCard;
import com.biglobby.entity.ShareCard;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.CardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cards")
public class CardRestController {

	@Autowired
	private CardService cardService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping
	public ResponseEntity<List<Card>> get() {
		return cardService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Card> get(@PathVariable("id") Long id) {
		return cardService.get(id);
	}

	@PostMapping
	public ResponseEntity<Card> add(@RequestBody Card card) {
		return cardService.add(card);
	}

	@PutMapping("/{id}")
	@SendTo("/cards/update")
	public ResponseEntity<Card> update(@PathVariable("id") Long id, @RequestBody Card card) {
		simpMessagingTemplate.convertAndSend("/" + Broker.CARD + "/" + BrokerAction.UPDATE, "asdfasdfasdfsd");
		return cardService.update(id, card);
	}

	@GetMapping(value = { "/{id}" }, params = { "countof" })
	public ResponseEntity<Long> getCount(@PathVariable("id") Long id, @RequestParam("countof") String collectionName) {
		switch (collectionName) {
			case "lovecards":
				return cardService.getCountOfLoves(id);
			case "comments":
				return cardService.getCountOfComments(id);
			case "shares":
				return cardService.getCountOfShares(id);
			default:
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return cardService.delete(id);
	}

	@GetMapping("/{id}/post")
	public ResponseEntity<Post> getPost(@PathVariable("id") Long id) {
		return cardService.getPost(id);
	}

	@GetMapping("/{id}/product")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
		return cardService.getProduct(id);
	}

	@GetMapping("/{id}/lovecards")
	public ResponseEntity<List<LoveCard>> getLoves(@PathVariable("id") Long id) {
		return cardService.getLoves(id);
	}

	@GetMapping("/{id}/comments")
	public ResponseEntity<List<CommentCard>> getComments(@PathVariable("id") Long id) {
		return cardService.getComments(id);
	}

	@GetMapping(value = "/{id}/comments", params = { "page", "limit" })
	public ResponseEntity<Page<CommentCard>> getPageComments(@PathVariable("id") Long id,
			@RequestParam("page") Integer pagenum, @RequestParam("limit") Integer limit) {
		return cardService.getPageComments(id, pagenum, limit);
	}

	@GetMapping("/{id}/shares")
	public ResponseEntity<List<ShareCard>> getShares(@PathVariable("id") Long id) {
		return cardService.getShares(id);
	}

	@GetMapping("/{id}/hides")
	public ResponseEntity<List<HideCard>> getHides(@PathVariable("id") Long id) {
		return cardService.getHides(id);
	}

	@GetMapping("/{id}/reports")
	public ResponseEntity<List<ReportCard>> getReports(@PathVariable("id") Long id) {
		return cardService.getReports(id);
	}

	@GetMapping("/{id}/failhistories")
	public ResponseEntity<List<FailHistory>> getFailHistories(@PathVariable("id") Long id) {
		return cardService.getFailHistories(id);
	}

	@GetMapping("/{id}/subbanners")
	public ResponseEntity<List<CardSubBanner>> getSubbanners(@PathVariable("id") Long id) {
		return cardService.getSubBanners(id);
	}
}
