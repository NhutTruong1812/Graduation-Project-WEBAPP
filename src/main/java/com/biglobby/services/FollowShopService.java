package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.FollowShop; 

public interface FollowShopService {

	public ResponseEntity<FollowShop> get(Long id);

	public ResponseEntity<List<FollowShop>> get();

	public ResponseEntity<FollowShop> add(FollowShop entity);

	public ResponseEntity<FollowShop> update(Long id, FollowShop entity);

	public ResponseEntity<Integer> delete(Long id);

	//ALL  
	public ResponseEntity<Page<FollowShop>> getListFollowALL(Long user,Pageable pageable);
	
	public ResponseEntity<Page<FollowShop>> getListFollowByShop(Long idShop,Pageable pageable);
	
}
