package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Card;
import com.biglobby.entity.Category;
import com.biglobby.entity.Order;
import com.biglobby.entity.Status;

public interface StatusService {

	public ResponseEntity<Status> get(Long id);

	public ResponseEntity<List<Status>> get();

	public ResponseEntity<Status> add(Status entity);

	public ResponseEntity<Status> update(Long id, Status entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<Category>> getCategories(Long statusId);

	public ResponseEntity<List<Card>> getCards(Long statusId);

	public ResponseEntity<List<Order>> getOrders(Long statusId);
}
