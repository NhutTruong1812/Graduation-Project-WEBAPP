package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.BServiceSubBanner;

public interface BServiceSubBannerService {

	public ResponseEntity<BServiceSubBanner> get(Long id);

	public ResponseEntity<List<BServiceSubBanner>> get();

	public ResponseEntity<BServiceSubBanner> add(BServiceSubBanner entity);

	public ResponseEntity<BServiceSubBanner> update(Long id, BServiceSubBanner entity);

	public ResponseEntity<Integer> delete(Long id);
}
