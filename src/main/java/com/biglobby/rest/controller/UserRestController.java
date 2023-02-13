package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.biglobby.entity.Address;
import com.biglobby.entity.Authority;
import com.biglobby.entity.BCoinHistory;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.BServicePrice;
import com.biglobby.entity.Cart;
import com.biglobby.entity.Category;
import com.biglobby.entity.CommentCard;
import com.biglobby.entity.DisplayFeeHistory;
import com.biglobby.entity.FailHistory;
import com.biglobby.entity.FollowShop;
import com.biglobby.entity.HideCard;
import com.biglobby.entity.LoveCard;
import com.biglobby.entity.MyBCoin;
import com.biglobby.entity.MyBService;
import com.biglobby.entity.MyShop;
import com.biglobby.entity.News;
import com.biglobby.entity.Order;
import com.biglobby.entity.PageTemp;
import com.biglobby.entity.ProductHistory;
import com.biglobby.entity.RegisterActive;
import com.biglobby.entity.RepComment;
import com.biglobby.entity.ReportCard;
import com.biglobby.entity.ShareCard;
import com.biglobby.entity.User;
import com.biglobby.services.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		return userService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") Long id) {
		return userService.get(id);
	}

	@GetMapping(params = { "username" })
	public ResponseEntity<User> getByUsername(@RequestParam("username") String username) {
		return userService.getByUsername(username);
	}

	@GetMapping(params = { "email" })
	public ResponseEntity<User> getByEmail(@RequestParam("email") String email) {
		return userService.getByEmail(email);
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<User> updateStatus(@PathVariable("id") Long id) {
		return userService.updateStatus(id);
	}

	@PostMapping
	public ResponseEntity<User> add(@RequestBody User user) {
		return userService.add(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable("id") Long id) {
		return userService.update(id, user);
	}

	@PutMapping("/{id}/changepass")
	public ResponseEntity<User> changePassword(@RequestBody User user, @PathVariable("id") Long id) {
		return userService.updatePassword(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return userService.delete(id);
	}

	@GetMapping("/{id}/newssents")
	public ResponseEntity<List<News>> getNewsSents(@PathVariable("id") Long userId) {
		return userService.getNewsSent(userId);
	}

	@GetMapping("/{id}/newsgots")
	public ResponseEntity<List<News>> getNewsGots(@PathVariable("id") Long userId) {
		return userService.getNewsGot(userId);
	}

	@GetMapping("/{id}/authorities")
	public ResponseEntity<List<Authority>> getAuthories(@PathVariable("id") Long userId) {
		return userService.getAuthories(userId);
	}

	@GetMapping("/{id}/bcoinhistories")
	public ResponseEntity<List<BCoinHistory>> getBCoinHistoreis(@PathVariable("id") Long userId) {
		return userService.getBCoinHistories(userId);
	}

	@GetMapping("/{id}/bcoin")
	public ResponseEntity<MyBCoin> getBCoin(@PathVariable("id") Long userId) {
		return userService.getBCoin(userId);
	}

	@GetMapping("/{id}/registeractive")
	public ResponseEntity<RegisterActive> getRegisterActive(@PathVariable("id") Long userId) {
		return userService.getRegisterActive(userId);
	}

	@GetMapping("/{id}/actiononbcoinhistories")
	public ResponseEntity<List<BCoinHistory>> getActionOnBCoinHistories(@PathVariable("id") Long userId) {
		return userService.getActionOnBCoinHistories(userId);
	}

	@GetMapping("/{id}/bservices")
	public ResponseEntity<List<MyBService>> getBServices(@PathVariable("id") Long userId) {
		return userService.getBServices(userId);
	}

	@GetMapping("/{id}/bservicehistories")
	public ResponseEntity<List<BServiceHistory>> getBServiceHistories(@PathVariable("id") Long userId) {
		return userService.getBServiceHistories(userId);
	}

	@GetMapping("/{id}/actiononbservicehistories")
	public ResponseEntity<List<BServiceHistory>> getActionOnBServiceHistories(@PathVariable("id") Long userId) {
		return userService.getActionOnBServiceHistories(userId);
	}

	@GetMapping("/{id}/bserviceprices")
	public ResponseEntity<List<BServicePrice>> getBServicePrices(@PathVariable("id") Long userId) {
		return userService.getBServicePrices(userId);
	}

	@GetMapping("/{id}/shop")
	public ResponseEntity<MyShop> getShop(@PathVariable("id") Long userId) {
		return userService.getShop(userId);
	}

	@GetMapping("/{id}/addresses")
	public ResponseEntity<List<Address>> getAddresses(@PathVariable("id") Long userId) {
		return userService.getAddresses(userId);
	}

	@GetMapping("/{id}/followshops")
	public ResponseEntity<List<FollowShop>> getFollowShops(@PathVariable("id") Long userId) {
		return userService.getFollowShops(userId);
	}

	@GetMapping("/{id}/displayfeehistories")
	public ResponseEntity<List<DisplayFeeHistory>> getDisplayFeeHistories(@PathVariable("id") Long userId) {
		return userService.getDisplayFeeHistories(userId);
	}

	@GetMapping("/{id}/categories")
	public ResponseEntity<List<Category>> getCategories(@PathVariable("id") Long userId) {
		return userService.getCategories(userId);
	}

	@GetMapping("/{id}/producthistories")
	public ResponseEntity<List<ProductHistory>> getProductHistories(@PathVariable("id") Long userId) {
		return userService.getProductHistories(userId);
	}

	@GetMapping("/{id}/orderofbuys")
	public ResponseEntity<List<Order>> getOrderOfBuys(@PathVariable("id") Long userId) {
		return userService.getOrderOfBuys(userId);
	}

	@GetMapping("/{id}/orderofsales")
	public ResponseEntity<List<Order>> getOrderOfSales(@PathVariable("id") Long userId) {
		return userService.getOrderOfSales(userId);
	}

	@GetMapping("/{id}/lovecards")
	public ResponseEntity<List<LoveCard>> getLoveCards(@PathVariable("id") Long userId) {
		return userService.getLoveCards(userId);
	}

	@GetMapping("/{id}/commentcards")
	public ResponseEntity<List<CommentCard>> getComments(@PathVariable("id") Long userId) {
		return userService.getCommentCards(userId);
	}

	@GetMapping("/{id}/replys")
	public ResponseEntity<List<RepComment>> getReplys(@PathVariable("id") Long userId) {
		return userService.getRepComments(userId);
	}

	@GetMapping("/{id}/sharecards")
	public ResponseEntity<List<ShareCard>> getShares(@PathVariable("id") Long userId) {
		return userService.getShareCards(userId);
	}

	@GetMapping("/{id}/hidecards")
	public ResponseEntity<List<HideCard>> getHideCards(@PathVariable("id") Long userId) {
		return userService.getHideCards(userId);
	}

	@GetMapping("/{id}/reportcards")
	public ResponseEntity<List<ReportCard>> getReportCards(@PathVariable("id") Long userId) {
		return userService.getReportCards(userId);
	}

	@GetMapping("/{id}/failhistories")
	public ResponseEntity<List<FailHistory>> getFailHistories(@PathVariable("id") Long userId) {
		return userService.getFailHistories(userId);
	}

	@GetMapping("/{id}/carts")
	public ResponseEntity<List<Cart>> getCarts(@PathVariable("id") Long userId) {
		return userService.getCarts(userId);
	}

	@GetMapping("/{id}/carts/totals")
	public ResponseEntity<Double> getTotalsCart(@PathVariable("id") Long id) {
		return userService.getTotalCarts(id);
	}

	// Thống kê của Hồ
	@GetMapping(params = { "id", "page", "limit", "status", "startDate", "endDate" })
	public ResponseEntity<PageTemp<List<Object[]>>> getStatistical(@RequestParam("id") long id,
			@RequestParam("page") int Npage, @RequestParam("limit") int Nitem, @RequestParam("status") int status,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		if (status < 0) {
			return null;
		} else {
			if (status == 1) {
				List<Object[]> page = userService.statistical(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, 0, page.size()));
			} else if (status == 2) {
				List<Object[]> page = userService.statisticalDonHang(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 3) {
				List<Object[]> page = userService.statisticalDonThanhCong(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 4) {
				List<Object[]> page = userService.statisticalHangBanDuoc(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 5) {
				List<Object[]> page = userService.statisticalHangTon(id).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 6) {
				List<Object[]> page = userService.statisticalTongKhachHang(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 7) {
				List<Object[]> page = userService.statisticalKhachHangQuayLai(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 8) {
				List<Object[]> page = userService.statisticalKhachHangMoi(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else if (status == 9) {
				List<Object[]> page = userService.statisticalTheoDoiMoi(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, Npage - 1, Nitem));
			} else {
				List<Object[]> page = userService.statistical(id, startDate, endDate).getBody();
				return ResponseEntity.ok(new PageTemp(page, 0, page.size()));
			}

		}
	}
	
	@GetMapping(params = {"idUser", "StatisticalInfoShop"})
	public ResponseEntity<List<Object[]>> getStatisticalInfoShop(@RequestParam("idUser") Long idUser, @RequestParam("StatisticalInfoShop") int StatisticalInfoShop) {
		return userService.statisticalInfoShop(idUser);
	}

}
