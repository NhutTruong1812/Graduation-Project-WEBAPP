package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
 
import com.biglobby.entity.OrderDetail;
import com.biglobby.repository.OrderDetailRepository;
import com.biglobby.services.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository odRepo;

	@Override
	public ResponseEntity<OrderDetail> get(Long id) {
		Optional<OrderDetail> od = odRepo.findById(id);
		if (od.isPresent()) {
			return ResponseEntity.ok(od.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<OrderDetail>> get() {
		List<OrderDetail> ods = odRepo.findAll();
		return ResponseEntity.ok(ods);
	}

	@Override
	public ResponseEntity<OrderDetail> add(OrderDetail entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		OrderDetail added = odRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<OrderDetail> update(Long id, OrderDetail entity) {
		Optional<OrderDetail> exist = odRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		OrderDetail updated = odRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<OrderDetail> exist = odRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = odRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// phần của quang dùng cho đối tác
	@Override
	public ResponseEntity<Page<OrderDetail>> getListOrderDetailAllById(Long id, Pageable pageable) {
		Optional<Page<OrderDetail>> OrderDetail = odRepo.getListOrderDetailAllById(id, pageable);
		return ResponseEntity.ok(OrderDetail.get());
	}

	@Override
	public ResponseEntity<Page<OrderDetail>> getListOrderDetailAllByIdSearch(Long id, String textSearh, Pageable pageable) {
		Optional<Page<OrderDetail>> OrderDetail = odRepo.getListOrderDetailAllByIdSearch(id, textSearh, pageable);
		return ResponseEntity.ok(OrderDetail.get());
	}

	@Override
	public ResponseEntity<Long> getQuantityOrderDetail(Long user, Long status) {
		try {
			 Long sum = odRepo.getSumQuantity(user, status).get(); 
			 return ResponseEntity.ok(sum);
		} catch (Exception e) {
			return ResponseEntity.ok(0l);
		} 
	}

	@Override
	public ResponseEntity<List<OrderDetail>> getListOrderDetailbyOrderId(Long id) {
		Optional<List<OrderDetail>> OrderDetail = odRepo.getOrderDetailByIdOrder(id);
		return ResponseEntity.ok(OrderDetail.get());
	}

}
