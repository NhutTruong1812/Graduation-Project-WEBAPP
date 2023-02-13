package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.CardSubBanner;

public interface CardSubBannerService {

	public ResponseEntity<CardSubBanner> get(Long id);

	public ResponseEntity<List<CardSubBanner>> get();

	public ResponseEntity<CardSubBanner> add(CardSubBanner entity);

	public ResponseEntity<CardSubBanner> update(Long id, CardSubBanner entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<Integer> deleteByIdCard(Long idCard);

	public ResponseEntity<List<CardSubBanner>> getCardsubbannerbyCardId(Long idCard);
}
