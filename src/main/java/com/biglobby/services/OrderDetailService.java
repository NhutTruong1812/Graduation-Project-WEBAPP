package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
 
import com.biglobby.entity.OrderDetail;

public interface OrderDetailService {

	public ResponseEntity<OrderDetail> get(Long id);

	public ResponseEntity<List<OrderDetail>> get();

	public ResponseEntity<OrderDetail> add(OrderDetail entity);

	public ResponseEntity<OrderDetail> update(Long id, OrderDetail entity);

	public ResponseEntity<Integer> delete(Long id);
	
	// phần của quang dùng cho đối tác
	//ALL  
	public ResponseEntity<Page<OrderDetail>> getListOrderDetailAllById(Long id, Pageable pageable);
						
	//ALL SEARCH 
	public ResponseEntity<Page<OrderDetail>> getListOrderDetailAllByIdSearch(Long id, String textSearh, Pageable pageable);

	public ResponseEntity<Long> getQuantityOrderDetail(Long user, Long status);
	
	public ResponseEntity<List<OrderDetail>> getListOrderDetailbyOrderId(Long id);
	
}
