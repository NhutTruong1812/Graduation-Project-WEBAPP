package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.CommentCard;
import com.biglobby.entity.RepComment;

public interface CommentCardService {

	public ResponseEntity<CommentCard> get(Long id);

	public ResponseEntity<List<CommentCard>> get();

	public ResponseEntity<List<CommentCard>> getByCardId(Long cardId);

	public ResponseEntity<CommentCard> add(CommentCard commentCard);

	public ResponseEntity<CommentCard> update(Long id, CommentCard commentCard);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<RepComment>> getReplys(Long commentId);

}
