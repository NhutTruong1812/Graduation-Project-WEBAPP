package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

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
import com.biglobby.entity.ProductHistory;
import com.biglobby.entity.RegisterActive;
import com.biglobby.entity.RepComment;
import com.biglobby.entity.ReportCard;
import com.biglobby.entity.ShareCard;
import com.biglobby.entity.User;

public interface UserService {

	public ResponseEntity<User> getByUsername(String username);

	public ResponseEntity<User> getByEmail(String email);

	public ResponseEntity<User> updateStatus(Long id);

	public ResponseEntity<User> get(Long id);

	public ResponseEntity<List<User>> get();

	public ResponseEntity<User> add(User user);

	public ResponseEntity<User> update(Long id, User user);

	public ResponseEntity<User> updatePassword(Long id, User user);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<News>> getNewsSent(Long userId);

	public ResponseEntity<List<News>> getNewsGot(Long userId);

	public ResponseEntity<List<Authority>> getAuthories(Long userId); 

	public ResponseEntity<List<BCoinHistory>> getBCoinHistories(Long userId);

	public ResponseEntity<MyBCoin> getBCoin(Long userId); 
  
	public ResponseEntity<RegisterActive> getRegisterActive(Long userId); 

	public ResponseEntity<List<BCoinHistory>> getActionOnBCoinHistories(Long userId);

	public ResponseEntity<List<MyBService>> getBServices(Long userId); 

	public ResponseEntity<List<BServiceHistory>> getBServiceHistories(Long userId);

	public ResponseEntity<List<BServiceHistory>> getActionOnBServiceHistories(Long userId);

	public ResponseEntity<List<BServicePrice>> getBServicePrices(Long userId);

	public ResponseEntity<MyShop> getShop(Long userId);

	public ResponseEntity<List<Address>> getAddresses(Long userId); 

	public ResponseEntity<List<FollowShop>> getFollowShops(Long userId);

	public ResponseEntity<List<DisplayFeeHistory>> getDisplayFeeHistories(Long userId);

	public ResponseEntity<List<Category>> getCategories(Long userId);

	public ResponseEntity<List<ProductHistory>> getProductHistories(Long userId);

	public ResponseEntity<List<Order>> getOrderOfBuys(Long userId);

	public ResponseEntity<List<Order>> getOrderOfSales(Long userId);

	public ResponseEntity<List<LoveCard>> getLoveCards(Long userId);

	public ResponseEntity<List<CommentCard>> getCommentCards(Long userId);

	public ResponseEntity<List<RepComment>> getRepComments(Long userId);

	public ResponseEntity<List<ShareCard>> getShareCards(Long userId);

	public ResponseEntity<List<HideCard>> getHideCards(Long userId);

	public ResponseEntity<List<ReportCard>> getReportCards(Long userId);

	public ResponseEntity<List<FailHistory>> getFailHistories(Long userId);

	public ResponseEntity<List<Cart>> getCarts(Long userId); 

	public ResponseEntity<Double> getTotalCarts(Long userId);

	// Thống kê của Hồ
	public ResponseEntity<List<Object[]>> statistical(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalDonHang(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalDonThanhCong(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalHangBanDuoc(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalHangTon(long id);

	public ResponseEntity<List<Object[]>> statisticalTongKhachHang(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalKhachHangQuayLai(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalKhachHangMoi(long id, String startDate, String endDate);

	public ResponseEntity<List<Object[]>> statisticalTheoDoiMoi(long id, String startDate, String endDate);
	
	public ResponseEntity<List<Object[]>> statisticalInfoShop(long idUser);

}
