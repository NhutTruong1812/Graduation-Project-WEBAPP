package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.RepComment;
import com.biglobby.repository.RepCommentRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.RepCommentService;

@Service
public class RepCommentServiceImpl implements RepCommentService {

	@Autowired
	private RepCommentRepository replyRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<RepComment> get(Long id) {
		Optional<RepComment> reply = replyRepo.findById(id);
		if (reply.isPresent()) {
			return ResponseEntity.ok(reply.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<RepComment>> get() {
		List<RepComment> replys = replyRepo.findAll();
		return ResponseEntity.ok(replys);
	}

	@Override
	public ResponseEntity<RepComment> add(RepComment entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		RepComment added = replyRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.REP_COMMENT + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<RepComment> update(Long id, RepComment entity) {
		Optional<RepComment> exist = replyRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		RepComment updated = replyRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.REP_COMMENT + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<RepComment> exist = replyRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = replyRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.REP_COMMENT + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
