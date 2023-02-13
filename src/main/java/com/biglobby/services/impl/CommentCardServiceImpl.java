package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.CommentCard;
import com.biglobby.entity.RepComment;
import com.biglobby.repository.CommentCardRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.CommentCardService;

@Service
public class CommentCardServiceImpl implements CommentCardService {

	@Autowired
	private CommentCardRepository commentCardRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<CommentCard> get(Long id) {
		Optional<CommentCard> commentCard = commentCardRepo.findById(id);
		if (commentCard.isPresent()) {
			return ResponseEntity.ok(commentCard.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<CommentCard>> get() {
		List<CommentCard> commentCards = commentCardRepo.findAll();
		return ResponseEntity.ok(commentCards);
	}

	@Override
	public ResponseEntity<List<CommentCard>> getByCardId(Long cardId) {
		List<CommentCard> comments = commentCardRepo.getCommentByCardId(cardId);
		return ResponseEntity.ok(comments);
	}

	@Override
	public ResponseEntity<CommentCard> add(CommentCard commentCard) {
		if (commentCard.getId() != null) {
			commentCard.setId(null);
		}
		CommentCard added = commentCardRepo.save(commentCard);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.COMMENT + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<CommentCard> update(Long id, CommentCard commentCard) {
		Optional<CommentCard> exist = commentCardRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		commentCard.setId(id);
		CommentCard updated = commentCardRepo.save(commentCard);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.COMMENT + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<CommentCard> exist = commentCardRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = commentCardRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.COMMENT + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<RepComment>> getReplys(Long commentId) {
		Optional<CommentCard> comment = commentCardRepo.findById(commentId);
		if (!comment.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(comment.get().getReplys());
	}
}
