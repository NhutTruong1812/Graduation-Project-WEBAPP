package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.ShareCard;
import com.biglobby.repository.ShareCardRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.ShareCardService;

@Service
public class ShareCardServiceImpl implements ShareCardService {

	@Autowired
	private ShareCardRepository scRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<ShareCard> get(Long id) {
		Optional<ShareCard> sc = scRepo.findById(id);
		if (sc.isPresent()) {
			return ResponseEntity.ok(sc.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<ShareCard>> get() {
		List<ShareCard> scs = scRepo.findAll();
		return ResponseEntity.ok(scs);
	}

	@Override
	public ResponseEntity<ShareCard> add(ShareCard entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		ShareCard added = scRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.SHARE + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ShareCard> update(Long id, ShareCard entity) {
		Optional<ShareCard> exist = scRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		ShareCard updated = scRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.SHARE + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<ShareCard> exist = scRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = scRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.SHARE + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
