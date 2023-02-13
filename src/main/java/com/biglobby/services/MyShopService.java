package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.FollowShop;
import com.biglobby.entity.MyShop;

public interface MyShopService {

	public ResponseEntity<MyShop> get(Long id);

	public ResponseEntity<List<MyShop>> get();

	public ResponseEntity<MyShop> add(MyShop entity);

	public ResponseEntity<MyShop> update(Long id, MyShop entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<FollowShop>> getFollows(Long shopId);
 
	public ResponseEntity<FollowShop> getFollow(Long fid);

	public ResponseEntity<FollowShop> addFollow(FollowShop fl);

	public ResponseEntity<FollowShop> updateFollow(Long fid, FollowShop fl);

	public ResponseEntity<Integer> deleteFollow(Long fid);
  
	public ResponseEntity<MyShop> getShopByUser(Long id);
 
}
