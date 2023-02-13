package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import com.biglobby.entity.OrderDetail;
import com.biglobby.entity.Product;
import com.biglobby.services.OrderDetailService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailRestController {

	@Autowired
	private OrderDetailService odService;

	@GetMapping
	public ResponseEntity<List<OrderDetail>> get() {
		return odService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDetail> get(@PathVariable("id") Long id) {
		return odService.get(id);
	}

	@PostMapping
	public ResponseEntity<OrderDetail> add(@RequestBody OrderDetail entity) {
		return odService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderDetail> update(@PathVariable("id") Long id, @RequestBody OrderDetail entity) {
		return odService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return odService.delete(id);
	}
	
	// phần của quang dùng cho đối tác
			@GetMapping(params = {"page", "limit", "status", "key", "user"})
			public ResponseEntity<Page<OrderDetail>> getAdminOrderDetail(
					@RequestParam("page") int Npage,
					@RequestParam("limit") int Nitem 
					,@RequestParam("status") int status,
					@RequestParam("key") String textSearch, 
					@RequestParam("user") Long id) {
				Pageable pageable = PageRequest.of(Npage -1, Nitem); 
				//list page OrderDetail
				if(status < 0) { 
					if(status == -1) {   
						return odService.getListOrderDetailAllByIdSearch(id, textSearch ,pageable);
					}else {
						return odService.getListOrderDetailAllByIdSearch(id, textSearch ,pageable);
					}
				}else {
					if(status == 1) {   
						return odService.getListOrderDetailAllById(id, pageable);
					}else {
						return odService.getListOrderDetailAllById(id, pageable);
					}
				}
			}
			// end
	//---truoengnvnv
	@GetMapping("/sum/quantity/{status}/{user}")
	public ResponseEntity<Long> getSumQuantity(
			@PathVariable("status") Long status ,
			@PathVariable("user")Long user){ 
		return odService.getQuantityOrderDetail(user, 7l); 
	}
	
	@GetMapping(params = {"order.id"})
	public ResponseEntity<List<OrderDetail>> getProductbyOrder(
			@RequestParam("order.id") Long orderid){ 
		return odService.getListOrderDetailbyOrderId(orderid);
	}
}
