package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.FollowShop;
import com.biglobby.repository.FollowShopRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.FollowShopService;

@Service
public class FollowShopServiceImpl implements FollowShopService {

	@Autowired
	private FollowShopRepository fsRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<FollowShop> get(Long id) {
		Optional<FollowShop> followShop = fsRepo.findById(id);
		if (followShop.isPresent()) {
			return ResponseEntity.ok(followShop.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<FollowShop>> get() {
		List<FollowShop> followShops = fsRepo.findAll();
		return ResponseEntity.ok(followShops);
	}

	@Override
	public ResponseEntity<FollowShop> add(FollowShop entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		FollowShop added = fsRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.FOLLOW_SHOP + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<FollowShop> update(Long id, FollowShop entity) {
		Optional<FollowShop> exist = fsRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		FollowShop updated = fsRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.FOLLOW_SHOP + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<FollowShop> exist = fsRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = fsRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.FOLLOW_SHOP + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Page<FollowShop>> getListFollowALL(Long user, Pageable pageable) {
		Optional<Page<FollowShop>> followShops = fsRepo.getListFollowShopALL(user, pageable);
		return ResponseEntity.ok(followShops.get());
	}

	@Override
	public ResponseEntity<Page<FollowShop>> getListFollowByShop(Long idShop, Pageable pageable) {
		Optional<Page<FollowShop>> followShops = fsRepo.getListFollowShopByIDShop(idShop, pageable);
		return ResponseEntity.ok(followShops.get());
	}

}
