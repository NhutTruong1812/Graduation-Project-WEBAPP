package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.FollowShop;
import com.biglobby.entity.MyShop;
import com.biglobby.repository.FollowShopRepository;
import com.biglobby.repository.MyShopRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.MyShopService;

@Service
public class MyShopServiceImpl implements MyShopService {

	@Autowired
	private MyShopRepository msRepo;

	@Autowired
	private FollowShopRepository flRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<MyShop> get(Long id) {
		Optional<MyShop> myShop = msRepo.findById(id);
		if (myShop.isPresent()) {
			return ResponseEntity.ok(myShop.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<MyShop>> get() {
		List<MyShop> myShops = msRepo.findAll();
		return ResponseEntity.ok(myShops);
	}

	@Override
	public ResponseEntity<MyShop> add(MyShop entity) {
		MyShop added = msRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.SHOP + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<MyShop> update(Long id, MyShop entity) {
		Optional<MyShop> exist = msRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		MyShop updated = msRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.SHOP + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<MyShop> exist = msRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = msRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.SHOP + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<FollowShop>> getFollows(Long shopId) {
		Optional<MyShop> shop = msRepo.findById(shopId);
		if (!shop.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(shop.get().getFollows());
	}

	@Override
	public ResponseEntity<FollowShop> getFollow(Long fid) {
		Optional<FollowShop> followShop = flRepo.findById(fid);
		if (followShop.isPresent()) {
			return ResponseEntity.ok(followShop.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<FollowShop> addFollow(FollowShop fl) {
		if (fl.getId() != null) {
			fl.setId(null);
		}
		FollowShop added = flRepo.save(fl);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<FollowShop> updateFollow(Long fid, FollowShop fl) {
		Optional<FollowShop> exist = flRepo.findById(fid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		fl.setId(fid);
		FollowShop updated = flRepo.save(fl);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteFollow(Long fid) {
		Optional<FollowShop> exist = flRepo.findById(fid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = flRepo.removeById(fid);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<MyShop> getShopByUser(Long id) {
		Optional<MyShop> myShop = msRepo.findByUserId(id);
		return ResponseEntity.ok(myShop.get());
	}

}
