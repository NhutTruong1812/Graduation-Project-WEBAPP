package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.ShareCard;

public interface ShareCardService {

	public ResponseEntity<ShareCard> get(Long id);

	public ResponseEntity<List<ShareCard>> get();

	public ResponseEntity<ShareCard> add(ShareCard entity);

	public ResponseEntity<ShareCard> update(Long id, ShareCard entity);

	public ResponseEntity<Integer> delete(Long id);
}
