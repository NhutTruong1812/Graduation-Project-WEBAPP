package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.CardSubBanner;
import com.biglobby.repository.CardSubBannerRepository;
import com.biglobby.services.CardSubBannerService;

@Service
public class CardSubBannerServiceImpl implements CardSubBannerService {

	@Autowired
	private CardSubBannerRepository sbRepo;

	@Override
	public ResponseEntity<CardSubBanner> get(Long id) {
		Optional<CardSubBanner> sb = sbRepo.findById(id);
		if (sb.isPresent()) {
			return ResponseEntity.ok(sb.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<CardSubBanner>> get() {
		List<CardSubBanner> sbs = sbRepo.findAll();
		return ResponseEntity.ok(sbs);
	}

	@Override
	public ResponseEntity<CardSubBanner> add(CardSubBanner entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		CardSubBanner added = sbRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<CardSubBanner> update(Long id, CardSubBanner entity) {
		Optional<CardSubBanner> exist = sbRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		CardSubBanner updated = sbRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<CardSubBanner> exist = sbRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = sbRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Integer> deleteByIdCard(Long idCard) {
		Integer deletedRow = sbRepo.removeByIdCard(idCard);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<CardSubBanner>> getCardsubbannerbyCardId(Long idCard) {
		List<CardSubBanner> sbs = sbRepo.getListCardSubbannerbyCardId(idCard).get();
		return ResponseEntity.ok(sbs);
	}

}
