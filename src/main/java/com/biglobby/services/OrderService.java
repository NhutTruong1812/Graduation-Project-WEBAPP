package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.biglobby.entity.Order;
import com.biglobby.entity.OrderDetail;

public interface OrderService {

	public ResponseEntity<Order> get(Long id);

	public ResponseEntity<List<Order>> get();

	public ResponseEntity<Order> add(Order entity);

	public ResponseEntity<Order> update(Long id, Order entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<OrderDetail>> getOrderDetails(Long orderId);

	public ResponseEntity<OrderDetail> getOrderDetail(Long odId);

	public ResponseEntity<OrderDetail> addOrderDetail(OrderDetail od);

	public ResponseEntity<OrderDetail> updateOrderDetail(Long odId, OrderDetail od);

	public ResponseEntity<Integer> deleteOrderDetail(Long odId);

	public ResponseEntity<Integer> getMaxPageOrderSaler(Long id);

	public ResponseEntity<Page<Order>> getListPageSaler(Long id, Pageable pageable);

	/* Phần của Hồ */
	// ALL WHERE USER
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSERSaler(Long id, Long idStatus, Pageable pageable);

	// ALL WHERE SEARCH USER
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSERSearchSaler(Long id, Long idStatus, String textSearch,
			Pageable pageable);

	// ALL USER
	public ResponseEntity<Page<Order>> getListOrderAllUSERSaler(Long id, Pageable pageable);

	// ALL USER SEARCH
	public ResponseEntity<Page<Order>> getListOrderAllUSERSearchSaler(Long id, String textSearch, Pageable pageable);

	public ResponseEntity<Integer> getMaxPageOrderWhere(Long queryWhere);

	public ResponseEntity<List<Order>> getListOrderWhere(Long queryWhere);

	// ALL
	public ResponseEntity<Page<Order>> getListOrderAll(Pageable pageable);

	// ALL SEARCH
	public ResponseEntity<Page<Order>> getListOrderAllSearch(String textSearh, Pageable pageable);

	// ALL WHERE
	public ResponseEntity<Page<Order>> getListOrderAllWhere(Long idStatus, Pageable pageable);

	// ALL WHERE SEARCH
	public ResponseEntity<Page<Order>> getListOrderAllWhereSearch(Long idStatus, String textSearch, Pageable pageable);

	// ALL WHERE OR
	public ResponseEntity<Page<Order>> getListOrderAllWhereOr(Long idStatus1, Long idStatus2, Pageable pageable);

	// ALL WHERE OR SEARCH
	public ResponseEntity<Page<Order>> getListOrderAllWhereOrSearch(Long idStatus1, Long idStatus2, String textSearch,
			Pageable pageable);

	// phần của quang
	// ALL WHERE USER
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSER(Long id, Long idStatus, Pageable pageable);

	// ALL WHERE SEARCH USER
	public ResponseEntity<Page<Order>> getListOrderAllWhereUSERSearch(Long id, Long idStatus, String textSearch,
			Pageable pageable);

	// ALL USER
	public ResponseEntity<Page<Order>> getListOrderAllUSER(Long id, Pageable pageable);

	// ALL USER SEARCH
	public ResponseEntity<Page<Order>> getListOrderAllUSERSearch(Long id, String textSearch, Pageable pageable);
	// end phần của quang

}
