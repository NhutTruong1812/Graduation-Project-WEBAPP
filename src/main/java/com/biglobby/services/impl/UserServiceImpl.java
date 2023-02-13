package com.biglobby.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.biglobby.repository.CartRepository;
import com.biglobby.repository.UserRepository;
import com.biglobby.server.chat.entity.UserSchema;
import com.biglobby.server.chat.repository.UserSchemaRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	SessionServiceImpl session;

	@Autowired
	private UserSchemaRepository userSchemaRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<User> get(Long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<User>> get() {
		List<User> users = userRepo.findAll();
		return ResponseEntity.ok(users);
	}

	@Override
	public ResponseEntity<User> add(User user) {
		if (user.getId() != null) {
			user.setId(null);
		}
		user.setBlocked(false);
		user.setLastLogin(new Date());
		user.setPassword(encoder.encode(user.getPassword()));
		User added = userRepo.save(user);
		session.set("usersessionstatus", user.getId());
		if (added != null) {
			UserSchema userSchema = new UserSchema();
			userSchema.setUsername(added.getUsername());
			userSchema.setEmail(added.getEmail());
			userSchema.setFullname(added.getFullname());
			userSchemaRepo.save(userSchema);
			simpMessagingTemplate.convertAndSend(Broker.USER + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<User> update(Long id, User user) {
		Optional<User> exist = userRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		user.setId(id);
		User updated = userRepo.save(user);
		if (updated != null) {
			UserSchema userSchema = userSchemaRepo.findByUsername(updated.getUsername()).orElse(null);
			if (userSchema != null) {
				userSchema.setUsername(updated.getUsername());
				userSchema.setEmail(updated.getEmail());
				userSchema.setFullname(updated.getFullname());
				userSchemaRepo.save(userSchema);
			}
			simpMessagingTemplate.convertAndSend(Broker.USER + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<User> exist = userRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = userRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			UserSchema userSchema = userSchemaRepo.findByUsername(exist.get().getUsername()).orElse(null);
			if (userSchema != null) {
				userSchemaRepo.deleteById(userSchema.getId());
			}
			simpMessagingTemplate.convertAndSend(Broker.USER + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<News>> getNewsSent(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getNewsSents());
	}

	@Override
	public ResponseEntity<List<News>> getNewsGot(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getNewsGots());
	}

	@Override
	public ResponseEntity<List<Authority>> getAuthories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getAuthorities());
	}

	@Override
	public ResponseEntity<List<BCoinHistory>> getBCoinHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getBCoinHistories());
	}

	@Override
	public ResponseEntity<MyBCoin> getBCoin(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getBCoin());
	}

	@Override
	public ResponseEntity<RegisterActive> getRegisterActive(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getRegisterActive());
	}

	@Override
	public ResponseEntity<List<BCoinHistory>> getActionOnBCoinHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getActionOnBCoinHistories());
	}

	@Override
	public ResponseEntity<List<MyBService>> getBServices(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getBServices());
	}

	@Override
	public ResponseEntity<List<BServiceHistory>> getBServiceHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getBServiceHistories());
	}

	@Override
	public ResponseEntity<List<BServiceHistory>> getActionOnBServiceHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getActionOnBServiceHistory());
	}

	@Override
	public ResponseEntity<List<BServicePrice>> getBServicePrices(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getBServicePrices());
	}

	@Override
	public ResponseEntity<MyShop> getShop(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getShop());
	}

	@Override
	public ResponseEntity<List<Address>> getAddresses(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getAddresses());
	}

	@Override
	public ResponseEntity<List<FollowShop>> getFollowShops(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getFollowShops());
	}

	@Override
	public ResponseEntity<List<DisplayFeeHistory>> getDisplayFeeHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getDisplayFeeHistories());
	}

	@Override
	public ResponseEntity<List<Category>> getCategories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getCategories());
	}

	@Override
	public ResponseEntity<List<ProductHistory>> getProductHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getProductHistories());
	}

	@Override
	public ResponseEntity<List<Order>> getOrderOfBuys(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getOrderOfBuy());
	}

	@Override
	public ResponseEntity<List<Order>> getOrderOfSales(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getOrderOfSale());
	}

	@Override
	public ResponseEntity<List<LoveCard>> getLoveCards(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getLoveCards());
	}

	@Override
	public ResponseEntity<List<CommentCard>> getCommentCards(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getCommentCards());
	}

	@Override
	public ResponseEntity<List<RepComment>> getRepComments(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getReplys());
	}

	@Override
	public ResponseEntity<List<ShareCard>> getShareCards(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getShareCards());
	}

	@Override
	public ResponseEntity<List<HideCard>> getHideCards(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getHideCards());
	}

	@Override
	public ResponseEntity<List<ReportCard>> getReportCards(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getReportCards());
	}

	@Override
	public ResponseEntity<List<FailHistory>> getFailHistories(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getFailHistories());
	}

	@Override
	public ResponseEntity<List<Cart>> getCarts(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user.get().getCarts());
	}

	@Override
	public ResponseEntity<Double> getTotalCarts(Long userId) {
		Double total = 0.0;
		List<Cart> carts = cartRepo.findByUserId(userId);
		if (!carts.isEmpty()) {
			for (int i = 0; i < carts.size(); i++) {
				Cart cart = carts.get(i);
				int quantity = cart.getQuantity();
				Double price = cart.getProduct().getPrice();
				total += (quantity * price);
			}
		}
		return ResponseEntity.ok(total);
	}

	@Override
	public ResponseEntity<User> getByUsername(String username) {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<User> getByEmail(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<User> updateStatus(Long id) {
		Optional<User> exist = userRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			exist.get().setId(id);
			exist.get().setBlocked(true);
			User updated = userRepo.save(exist.get());
			if (updated != null) {
				return ResponseEntity.ok(updated);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<List<Object[]>> statistical(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statistical(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalDonHang(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalDonHang(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalDonThanhCong(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalDonThanhCong(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalHangBanDuoc(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalHangBanDuoc(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalHangTon(long id) {
		Optional<List<Object[]>> exist = userRepo.statisticalHangTon(id);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalTongKhachHang(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalTongKhachHang(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalKhachHangQuayLai(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalKhachHangQuayLai(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalKhachHangMoi(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalKhachHangMoi(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Object[]>> statisticalTheoDoiMoi(long id, String startDate, String endDate) {
		Optional<List<Object[]>> exist = userRepo.statisticalTheoDoiMoi(id, startDate, endDate);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<User> updatePassword(Long id, User user) {
		Optional<User> exist = userRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		user.setId(id);
		user.setPassword(encoder.encode(user.getPassword()));
		User updated = userRepo.save(user);
		if (updated != null) {
			UserSchema userSchema = userSchemaRepo.findByUsername(updated.getUsername()).orElse(null);
			if (userSchema != null) {
				userSchema.setUsername(updated.getUsername());
				userSchema.setEmail(updated.getEmail());
				userSchema.setFullname(updated.getFullname());
				userSchemaRepo.save(userSchema);
			}
			simpMessagingTemplate.convertAndSend(Broker.USER + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	

	@Override
	public ResponseEntity<List<Object[]>> statisticalInfoShop(long idUser) {
		Optional<List<Object[]>> exist = userRepo.statisticalInfoShop(idUser);
		return ResponseEntity.ok(exist.get());
	}

}
