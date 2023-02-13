package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.LoveCard;
import com.biglobby.repository.LoveCardRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.LoveCardService;

@Service
public class LoveCardServiceImpl implements LoveCardService {

	@Autowired
	private LoveCardRepository lcRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<LoveCard> get(Long id) {
		Optional<LoveCard> loveCard = lcRepo.findById(id);
		if (loveCard.isPresent()) {
			return ResponseEntity.ok(loveCard.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<LoveCard>> get() {
		List<LoveCard> loveCards = lcRepo.findAll();
		return ResponseEntity.ok(loveCards);
	}

	@Override
	public ResponseEntity<LoveCard> add(LoveCard entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		LoveCard added = lcRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.LOVECARD + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<LoveCard> update(Long id, LoveCard entity) {
		Optional<LoveCard> exist = lcRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		LoveCard updated = lcRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.LOVECARD + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<LoveCard> exist = lcRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = lcRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.LOVECARD + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<LoveCard> getByUserIdAndCardId(Long useId, Long cardId) {
		Optional<LoveCard> exist = lcRepo.findByUserIdAndCardId(useId, cardId);
		if (exist.isPresent()) {
			return ResponseEntity.ok(exist.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Page<LoveCard>> getListLoveCardAllByIdUser(Long id, Pageable pageable) {
		Optional<Page<LoveCard>> LoveCard = lcRepo.getListLoveCardAllByIdUser(id, pageable);
		return ResponseEntity.ok(LoveCard.get());
	}

	@Override
	public ResponseEntity<Page<LoveCard>> getListLoveCardAllByIdUserSearch(Long id, String textSearh,
			Pageable pageable) {
		Optional<Page<LoveCard>> LoveCard = lcRepo.getListLoveCardAllByIdUserSearch(id, textSearh, pageable);
		return ResponseEntity.ok(LoveCard.get());
	}

}
