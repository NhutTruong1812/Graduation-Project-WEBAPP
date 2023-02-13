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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.Cart;
import com.biglobby.entity.Filter;
import com.biglobby.entity.OrderDetail;
import com.biglobby.entity.Product;
import com.biglobby.entity.ProductHistory;
import com.biglobby.entity.ReportCard;
import com.biglobby.services.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> get() {
		return productService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> get(@PathVariable("id") Long id) {
		return productService.get(id);
	}

	@PostMapping
	public ResponseEntity<Product> add(@RequestBody Product product) {
		return productService.add(product);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody Product product) {
		return productService.update(id, product);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return productService.delete(id);
	}

	@GetMapping("/{id}/histories")
	public ResponseEntity<List<ProductHistory>> getHistories(@PathVariable("id") Long id) {
		return productService.getHistories(id);
	}

	@GetMapping("/{id}/orderdetails")
	public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable("id") Long id) {
		return productService.getOrderDetails(id);
	}

	@GetMapping("/{id}/carts")
	public ResponseEntity<List<Cart>> getCarts(@PathVariable("id") Long id) {
		return productService.getCarts(id);
	}

	@GetMapping("/maxpage/{hidden}")
	public ResponseEntity<Integer> getMaxPage(@PathVariable("hidden") boolean hidden) {
		return productService.getMaxPageProduct(hidden);
	}

	@GetMapping("/page/{Npage}/{Nitem}/{hidden}")
	public ResponseEntity<Page<Product>> getPage(@PathVariable("Npage") int Npage, @PathVariable("Nitem") int Nitem,
			@PathVariable("hidden") boolean hidden) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		return productService.getListPage(hidden, pageable);
	}

	@GetMapping(params= {"page", "limit", "pagewhere","key"})
	public ResponseEntity<Page<Product>> getPageProduct(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem,  
			@RequestParam("pagewhere") int pagewhere,
			@RequestParam("key") String textSearch) {
		boolean isProduct = true;
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");
		System.err.println("ADMIN PAGE PRODUCT");
		System.err.println("NPage: " + Npage);
		System.err.println("NItem: " + Nitem);
		System.err.println("Key: " + textSearch);
		System.err.println("pagewhere: " + pagewhere);
		System.err.println("isPro: " + isProduct);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");

		if (pagewhere < 0) {
			if (pagewhere == -1) {
				return productService.getListProductAllSearch(isProduct, 3l, textSearch, pageable);
			} else if (pagewhere == -2) {
				return productService.getListProductAllWhereSearch(isProduct, false, 3l, textSearch, pageable);
			} else if (pagewhere == -3) {
				return productService.getListProductAllWhereSearch(isProduct, true, 3l, textSearch, pageable);
			} else if (pagewhere == -4) {
				return productService.getListProductAllSearch(isProduct, 1l, textSearch, pageable);
			} else if (pagewhere == -5) {
				return productService.getListProductAllSearch(isProduct, 2l, textSearch, pageable);
			} else {
				return productService.getListProductAll(isProduct, pageable);
			}
		} else {
			if (pagewhere == 1) { 
				
				return productService.getListProductAll(isProduct, 3l, pageable);
			} else if (pagewhere == 2) {
				return productService.getListProductAllWhere(isProduct, false, 3l, pageable);
			} else if (pagewhere == 3) {
				return productService.getListProductAllWhere(isProduct, true, 3l, pageable);
			} else if (pagewhere == 4) {
				return productService.getListProductAll(isProduct, 1l, pageable);
			} else if (pagewhere == 5) {
				return productService.getListProductAll(isProduct, 2l, pageable);
			} else {
				return productService.getListProductAll(isProduct, pageable);
			}
		}
 
	}
	 
	/**
	 * haotn modified
	 * 
	 * @param Npage
	 * @param Nitem
	 * @param status
	 * @param textSearch
	 * @return
	 */

	/*
	 * @GetMapping(params = { "page", "limit", "card.status", "keyword" })
	 * public ResponseEntity<Page<Product>> getPageProduct(
	 * 
	 * @RequestParam("page") Integer pagenum,
	 * 
	 * @RequestParam("limit") Integer limit,
	 * 
	 * @RequestParam("status") Integer status,
	 * 
	 * @RequestParam("keyword") String keyword) {
	 * Pageable pageable = PageRequest.of(pagenum - 1, limit);
	 * switch (status) {
	 * case -5:
	 * return productService.getListProductAllSearch(true, 2l, keyword, pageable);
	 * case -4:
	 * return productService.getListProductAllSearch(true, 1l, keyword, pageable);
	 * case -3:
	 * return productService.getListProductAllWhereSearch(true, true, 3l, keyword,
	 * pageable);
	 * case -2:
	 * return productService.getListProductAllWhereSearch(true, false, 3l, keyword,
	 * pageable);
	 * case -1:
	 * return productService.getListProductAllSearch(true, 3l, keyword, pageable);
	 * case 1:
	 * return productService.getListProductAll(true, 3l, pageable);
	 * case 2:
	 * return productService.getListProductAllWhere(true, false, 3l, pageable);
	 * case 3:
	 * return productService.getListProductAllWhere(true, true, 3l, pageable);
	 * case 4:
	 * return productService.getListProductAll(true, 1l, pageable);
	 * case 5:
	 * return productService.getListProductAll(true, 2l, pageable);
	 * default:
	 * return productService.getListProductAll(true, pageable);
	 * }
	 * }
	 */
	//report
	@GetMapping(params= {"page", "limit", "pagewhere","key","report"})
	public ResponseEntity<Page<ReportCard>> getPageReport(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem,
			@RequestParam("pagewhere") int pagewhere,
			@RequestParam("key") String textSearch,
			@RequestParam("report") int report) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");
		System.err.println("NPage: " + Npage);
		System.err.println("NItem: " + Nitem);
		System.err.println("Key: " + textSearch);
		System.err.println("status: " + pagewhere);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");

		if (pagewhere < 0) {
			if (pagewhere == -6) {
				return productService.getListproductReportSeacrh(12l, textSearch, pageable);
			} else if (pagewhere == -7) {
				return productService.getListproductReportSeacrh(13l, textSearch, pageable);
			} else if (pagewhere == -8) {
				return productService.getListproductReportSeacrh(14l, textSearch, pageable);
			} else {
				return productService.getListproductReport(12l, pageable);
			}
		} else {
			if (pagewhere == 6) {
				return productService.getListproductReport(12l, pageable);
			} else if (pagewhere == 7) {
				return productService.getListproductReport(13l, pageable);
			} else if (pagewhere == 8) {
				return productService.getListproductReportSeacrh(14l, textSearch, pageable);
			} else {
				return productService.getListproductReport(12l, pageable);
			}
		}
	}

	// xem của hàng khác
	@GetMapping(params= {"page", "limit", "pagewhere","key", "user"})
	public ResponseEntity<Page<Product>> getPageProductUser(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem,
			@RequestParam("pagewhere") int pagewhere,
			@RequestParam("key") String textSearch,
			@RequestParam("user") Long user) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");
		System.err.println("NPage: " + Npage);
		System.err.println("NItem: " + Nitem);
		System.err.println("Key: " + textSearch);
		System.err.println("pagewhere: " + pagewhere);
		// System.err.println("isPro: " + isProduct);
		System.err.println("user: " + user);
		System.err.println("_________________________________________________________________________________");
		System.err.println("sdfsdfsd");

		if (pagewhere < 0) {
			if (pagewhere == -1) {
				return productService.getListLoveCardAllWhereUserSearch(true, false, 3l, user, textSearch, pageable);
			} else if (pagewhere == -2) {
				return productService.getListProductWhereMyShopALLSearch(true, false, 3l, user, textSearch, pageable);
			} else if (pagewhere == -3) {
				return productService.getListShopALL(true, false, 3l, user, pageable);
			} else {
				return productService.getListLoveCardAllWhereUser(true, false, 3l, user, pageable);
			}
		} else if (pagewhere == 0) {
			return productService.getListDemo(pageable);
		} else {
			if (pagewhere == 1) {
				return productService.getListLoveCardAllWhereUser(true, false, 3l, user, pageable);
			} else if (pagewhere == 2) {
				return productService.getListProductWhereMyShopALLSearch(true, false, 3l, user, textSearch, pageable);
			} else if (pagewhere == 3) {
				return productService.getListShopALL(true, false, 3l, user, pageable);
			} else if (pagewhere == 4) {
				Page<Product> p = productService.getListLoveCardAllWhereUser(true, false, 3l, user, pageable).getBody();
				int size = p.getSize();
				int totalPage = p.getTotalPages();
				Pageable pageAll = PageRequest.of(1, (size * totalPage));
				return productService.getListLoveCardAllWhereUser(true, false, 3l, user, pageAll);
			} else if (pagewhere == 5) {
				Page<Product> p = productService.getListProductWhereMyShopALLSearch(true, false, 3l, user,textSearch, pageable).getBody();
				int size = p.getSize();
				int totalPage = p.getTotalPages();
				Pageable pageAll = PageRequest.of(0, (size * totalPage));
				return productService.getListProductWhereMyShopALLSearch(true, false, 3l, user, textSearch, pageAll);
			} else {
				return productService.getListLoveCardAllWhereUser(true, false, 3l, user, pageable);
			}
		}
	}

	// phần của quang dung cho quản lý sản phẩm
	@GetMapping(params = {"page", "limit", "status", "key", "user"})
	public ResponseEntity<Page<Product>> getUserProduct(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, 
			@RequestParam("status") int status,
			@RequestParam("key") String textSearch, 
			@RequestParam("user") Long id) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page Product
		if (status < 0) {
			if (status == -1) {
				return productService.getListProductCartAllByIdUserSearch(id, textSearch, pageable);
			} else if (status == -2) {
				return productService.getListProductAllWhereUserStatusSearch(id, 3l, textSearch, pageable);
			} else if (status == -3) {
				return productService.getListProductAllWhereUserStatusSearch(id, 9l, textSearch, pageable);
			} else if (status == -4) {
				return productService.getListProductAllWhereUserStatusSearch(id, 1l, textSearch, pageable);
			} else if (status == -5) {
				return productService.getListProductAllWhereUserStatusSearch(id, 2l, textSearch, pageable);
			} else if (status == -6) {
				return productService.getListProductAllWhereUserStatusSearch(id, 4l, textSearch, pageable);
			} else {
				return productService.getListProductCartAllByIdUserSearch(id, textSearch, pageable);
			}
		} else {
			if (status == 1) {
				return productService.getListProductAllByIdUser(id, pageable);
			} else if (status == 2) {
				return productService.getListProductAllWhereUserStatus(id, 3l, pageable);
			} else if (status == 3) {
				return productService.getListProductAllWhereUserStatus(id, 9l, pageable);
			} else if (status == 4) {
				return productService.getListProductAllWhereUserStatus(id, 1l, pageable);
			} else if (status == 5) {
				return productService.getListProductAllWhereUserStatus(id, 2l, pageable);
			} else if (status == 6) {
				return productService.getListProductAllWhereUserStatus(id, 4l, pageable);
			} else {
				return productService.getListProductAllByIdUser(id, pageable);
			}
		}
	}
	// end

	// card
	@ResponseBody
	@PostMapping(params= {"page", "limit", "pagewhere"})
	public ResponseEntity<Page<Product>> getPageProductCuaHang(
			@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem,
			@RequestParam("pagewhere") int pagewhere,
			@RequestBody Filter filterObj) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");
		System.err.println("NPage: " + Npage);
		System.err.println("NItem: " + Nitem);
		System.err.println("Key: " + filterObj.getKey());
		System.err.println("status: " + pagewhere);
		System.err.println("fprice: " + filterObj.getFrom());
		System.err.println("tPrice: " + filterObj.getTo());
		for (int i = 0; i < filterObj.getCategory().length; i++) {
			System.err.println("Cate " + filterObj.getCategory()[i]);
		}
		for (int j = 0; j < filterObj.getLocation().length; j++) {
			System.err.println("Local " + filterObj.getLocation()[j]);
		}
		System.err.println("_________________________________________________________________________________");
		System.err.println("_________________________________________________________________________________");

		if (pagewhere == -200) {
			return productService.getListMyShopSearchAndFilter("%" + filterObj.getKey() + "%", filterObj.getFrom(),
					filterObj.getTo(), filterObj.getCategory(), filterObj.getLocation(), filterObj.getUser(), pageable);
		} else {
			return productService.getListSearchAndFilter("%" + filterObj.getKey() + "%", filterObj.getFrom(),
					filterObj.getTo(), filterObj.getCategory(), filterObj.getLocation(), filterObj.getUser(), pageable);
		}
	}

	@GetMapping("/{id}/cards/obj")
	public ResponseEntity<Product> getProductByCard(@PathVariable("id") Long id) {
		return productService.getProductByCard(id);
	}
}
