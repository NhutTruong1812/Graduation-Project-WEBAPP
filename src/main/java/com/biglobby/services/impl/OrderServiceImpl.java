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

import com.biglobby.entity.Order;
import com.biglobby.entity.OrderDetail;
import com.biglobby.repository.OrderDetailRepository;
import com.biglobby.repository.OrderRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository oRepo;

	@Autowired
	private OrderDetailRepository odRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<Order> get(Long id) {
		Optional<Order> order = oRepo.findById(id);
		if (order.isPresent()) {
			return ResponseEntity.ok(order.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Order>> get() {
		List<Order> orders = oRepo.findAll();
		return ResponseEntity.ok(orders);
	}

	@Override
	public ResponseEntity<Order> add(Order entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		Order added = oRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.ORDER + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Order> update(Long id, Order entity) {
		Optional<Order> exist = oRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		Order updated = oRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.ORDER + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Order> exist = oRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = oRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.ORDER + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<OrderDetail>> getOrderDetails(Long orderId) {
		Optional<Order> order = oRepo.findById(orderId);
		if (!order.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(order.get().getOrderDetails());
	}

	@Override
	public ResponseEntity<OrderDetail> getOrderDetail(Long odId) {
		Optional<OrderDetail> od = odRepo.findById(odId);
		if (od.isPresent()) {
			return ResponseEntity.ok(od.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<OrderDetail> addOrderDetail(OrderDetail od) {
		if (od.getId() != null) {
			od.setId(null);
		}
		OrderDetail added = odRepo.save(od);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<OrderDetail> updateOrderDetail(Long odId, OrderDetail od) {
		Optional<OrderDetail> exist = odRepo.findById(odId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		od.setId(odId);
		OrderDetail updated = odRepo.save(od);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteOrderDetail(Long odId) {
		Optional<OrderDetail> exist = odRepo.findById(odId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = odRepo.removeById(odId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> getMaxPageOrderSaler(Long id) {
		Optional<Integer> pd = oRepo.getMaxPageOrderSaler(id);
		return ResponseEntity.ok(pd.get());
	}

	/* Phần Của Hồ */
	// All where
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSERSaler(Long id, Long idStatus, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereUSERSaler(id, idStatus, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// all where search
	@Override
	public ResponseEntity<Page<Order>> getListPageSaler(Long id, Pageable pageable) {
		Optional<Page<Order>> list = oRepo.findPageSaler(id, pageable);
		return ResponseEntity.ok(list.get());
	}

	// Phần của quang
	// All where
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSER(Long id, Long idStatus, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereUSER(id, idStatus, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// all where search
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSERSearch(Long id, Long idStatus, String textSearch,
			Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereUSERSearch(id, idStatus, textSearch, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// All USER
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllUSER(Long id, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllUSER(id, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// <---by truongnvn
	@Override
	public ResponseEntity<Integer> getMaxPageOrderWhere(Long queryWhere) {
		Optional<Integer> list = oRepo.getMaxPageOrderWhere(queryWhere);
		return ResponseEntity.ok(list.get());
	}

	public ResponseEntity<Page<Order>> getListOrderAllWhereUSERSearchSaler(Long id, Long idStatus, String textSearch,
			Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereUSERSearchSaler(id, idStatus, textSearch, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// All USER
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllUSERSaler(Long id, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllUSERSaler(id, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// all USER search
	@Override
	public ResponseEntity<List<Order>> getListOrderWhere(Long queryWhere) {
		Optional<List<Order>> orders = oRepo.getListOrderWhere(queryWhere);
		return ResponseEntity.ok(orders.get());
	}

	public ResponseEntity<Page<Order>> getListOrderAllUSERSearchSaler(Long id, String textSearch, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllUSERSearchSaler(id, textSearch, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// --->
	// All
	@Override
	public ResponseEntity<Page<Order>> getListOrderAll(Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAll(pageable);
		return ResponseEntity.ok(orders.get());
	}

	// All Search
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllSearch(String textSearh, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllSearch(textSearh, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// All where
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhere(Long idStatus, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhere(idStatus, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// all where search
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhereSearch(Long idStatus, String textSearch, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereSearch(idStatus, textSearch, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// all where or all
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhereOr(Long idStatus1, Long idStatus2, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereOr(idStatus1, idStatus2, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// all where or search
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllWhereOrSearch(Long idStatus1, Long idStatus2, String textSearch,
			Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllWhereOrSearch(idStatus1, idStatus2, textSearch, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// --->
	// all USER search
	@Override
	public ResponseEntity<Page<Order>> getListOrderAllUSERSearch(Long id, String textSearch, Pageable pageable) {
		Optional<Page<Order>> orders = oRepo.getListOrderAllUSERSearch(id, textSearch, pageable);
		return ResponseEntity.ok(orders.get());
	}

	// end phần của quang

}
