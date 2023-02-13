package com.biglobby.rest.controller;

import java.util.ArrayList;
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
import com.biglobby.entity.Order;
import com.biglobby.entity.OrderDetail;
import com.biglobby.entity.PageTemp;
import com.biglobby.entity.OrderAndSumPriceOrder; 
import com.biglobby.services.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	@Autowired
	private OrderService oService;

	@GetMapping
	public ResponseEntity<List<Order>> get() {
		return oService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> get(@PathVariable("id") Long id) {
		return oService.get(id);
	}

	@PostMapping
	public ResponseEntity<Order> add(@RequestBody Order entity) {
		return oService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Order> update(@PathVariable("id") Long id, @RequestBody Order entity) {
		return oService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return oService.delete(id);
	}

	@GetMapping("/{id}/details")
	public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable("id") Long id) {
		return oService.getOrderDetails(id);
	}

	@GetMapping("/{id}/details/{did}")
	public ResponseEntity<OrderDetail> getOrderDetail(@PathVariable("did") Long did) {
		return oService.getOrderDetail(did);
	}

	@PostMapping("/details")
	public ResponseEntity<OrderDetail> addOrderDetail(@RequestBody OrderDetail od) {
		return oService.addOrderDetail(od);
	}

	@PutMapping("/details/{did}")
	public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable("did") Long did, @RequestBody OrderDetail od) {
		return oService.updateOrderDetail(did, od);
	}

	@DeleteMapping("/details/{did}")
	public ResponseEntity<Integer> deleteOrderDetail(@PathVariable("did") Long did) {
		return oService.deleteOrderDetail(did);
	}

	/**
	 * Relationship conflic
	 */
	@GetMapping("/maxpageSaler/{id}")
	public ResponseEntity<Integer> getMaxPageSaler(@PathVariable("id") Long id) {
		return oService.getMaxPageOrderSaler(id);
	}

	@GetMapping("/pageSaler/{Npage}/{Nitem}/{id}")
	public ResponseEntity<Page<Order>> getPageSaler(@PathVariable("Npage") int Npage, @PathVariable("Nitem") int Nitem,
			@PathVariable("id") Long id) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		return oService.getListPageSaler(id, pageable);
	}

	/**
	 * Order Page
	 */
	// <--by truongnvn, admin
	@GetMapping(params = {"page", "limit", "pagewhere","key"})
	public ResponseEntity<Page<Order>> getAdminOrder(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem,
			@RequestParam("pagewhere") int pagewhere,
			@RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page order
		if (pagewhere < 0) {
			if (pagewhere == -1) {
				return oService.getListOrderAllSearch(textSearch, pageable);
			} else if (pagewhere == -2) {
				return oService.getListOrderAllWhereSearch(5l, textSearch, pageable);
			} else if (pagewhere == -3) {
				return oService.getListOrderAllWhereSearch(6l, textSearch, pageable);
			} else if (pagewhere == -4) {
				return oService.getListOrderAllWhereSearch(7l, textSearch, pageable);
			} else if (pagewhere == -5) {
				return oService.getListOrderAllWhereOrSearch(8l, 9l, textSearch, pageable);
			} else if (pagewhere == -6) {
				return oService.getListOrderAllWhereSearch(8l, textSearch, pageable);
			} else if (pagewhere == -7) {
				return oService.getListOrderAllWhereSearch(9l, textSearch, pageable);
			} else {
				return oService.getListOrderAllSearch(textSearch, pageable);
			}
		} else {
			if (pagewhere == 1) {
				return oService.getListOrderAll(pageable);
			} else if (pagewhere == 2) {
				return oService.getListOrderAllWhere(5l, pageable);
			} else if (pagewhere == 3) {
				return oService.getListOrderAllWhere(6l, pageable);
			} else if (pagewhere == 4) {
				return oService.getListOrderAllWhere(7l, pageable);
			} else if (pagewhere == 5) {
				return oService.getListOrderAllWhereOr(8l, 9l, pageable);
			} else if (pagewhere == 6) {
				return oService.getListOrderAllWhere(8l, pageable);
			} else if (pagewhere == 7) {
				return oService.getListOrderAllWhere(9l, pageable);
			} else {
				return oService.getListOrderAll(pageable);
			}
		}

	}

	/* Phần của Hồ */
	@GetMapping(params = {"page", "limit", "status","key", "userSaler"})
	public ResponseEntity<PageTemp<OrderAndSumPriceOrder>> getUserOrderSaler(@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, 
			@RequestParam("status") int status,
			@RequestParam("key") String textSearch, @RequestParam("userSaler") Long id) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page order
		if (status < 0) {
			if (status == -8) {
				try {
					Page<Order> dso = oService.getListOrderAllUSERSearchSaler(id, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSERSearchSaler(id, textSearch, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == -9) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearchSaler(id, 5l, textSearch, pageable)
							.getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearchSaler(id, 5l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == -10) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearchSaler(id, 6l, textSearch, pageable)
							.getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearchSaler(id, 6l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == -11) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearchSaler(id, 7l, textSearch, pageable)
							.getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearchSaler(id, 7l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}

			} else if (status == -12) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearchSaler(id, 9l, textSearch, pageable)
							.getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearchSaler(id, 9l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else {
				try {
					Page<Order> dso = oService.getListOrderAllUSERSearchSaler(id, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSERSearchSaler(id, textSearch, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			}
		} else {
			if (status == 8) {
				try {
					Page<Order> dso = oService.getListOrderAllUSERSaler(id, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSERSaler(id, pageableSum).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			}

			else if (status == 9) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSaler(id, 5l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSaler(id, 5l, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == 10) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSaler(id, 6l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSaler(id, 6l, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == 11) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSaler(id, 7l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSaler(id, 7l, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == 12) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSaler(id, 9l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSaler(id, 9l, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else {
				try {
					Page<Order> dso = oService.getListOrderAllUSERSaler(id, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSERSaler(id, pageable).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			}

		}
	}

	/**
	 * Order Page
	 */
	// Phần của quang
	@GetMapping(params = {"page", "limit", "status","key", "user"})
	public ResponseEntity<PageTemp<OrderAndSumPriceOrder>> getUserOrder(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, 
			@RequestParam("status") int status,
			@RequestParam("key") String textSearch, @RequestParam("user") Long id) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page order

		if (status < 0) {
			if (status == -8) {
				try {
					Page<Order> dso = oService.getListOrderAllUSERSearch(id, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSERSearch(id, textSearch, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == -9) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearch(id, 5l, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearch(id, 5l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == -10) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearch(id, 6l, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearch(id, 6l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == -11) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearch(id, 7l, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearch(id, 7l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}

			} else if (status == -12) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSERSearch(id, 8l, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSERSearch(id, 8l, textSearch, pageableSum)
							.getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else {
				try {
					Page<Order> dso = oService.getListOrderAllUSERSearch(id, textSearch, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSERSearch(id, textSearch, pageableSum).getBody()
							.getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			}
		} else {
			if (status == 8) {
				try {
					Page<Order> dso = oService.getListOrderAllUSER(id, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSER(id, pageableSum).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			}

			else if (status == 9) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSER(id, 5l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSER(id, 5l, pageableSum).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == 10) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSER(id, 6l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSER(id, 6l, pageableSum).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == 11) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSER(id, 7l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSER(id, 7l, pageableSum).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else if (status == 12) {
				try {
					Page<Order> dso = oService.getListOrderAllWhereUSER(id, 8l, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllWhereUSER(id, 8l, pageableSum).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			} else {
				try {
					Page<Order> dso = oService.getListOrderAllUSER(id, pageable).getBody();
					int element = dso.getSize();
					int totalPage = dso.getTotalPages();
					Pageable pageableSum = PageRequest.of(0, (element * totalPage));
					List<Order> dso1 = oService.getListOrderAllUSER(id, pageable).getBody().getContent();
					List<OrderAndSumPriceOrder> dsosum = new ArrayList<OrderAndSumPriceOrder>();
					for (Order o : dso1) {
						dsosum.add(new OrderAndSumPriceOrder(o, o.getSumm()));
					}
					return ResponseEntity.ok(new PageTemp(dsosum, Npage - 1, Nitem));
				} catch (Exception e) {
					return ResponseEntity.ok(null);
				}
			}
		}
	}

	// end phần của quang

}
