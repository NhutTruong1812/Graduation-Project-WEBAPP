package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.HideCard;
import com.biglobby.repository.HideCardRepository;
import com.biglobby.services.HideCardService;

@Service
public class HideCardServiceImpl implements HideCardService {

	@Autowired
	private HideCardRepository hdRepo;

	@Override
	public ResponseEntity<HideCard> get(Long id) {
		Optional<HideCard> hideCard = hdRepo.findById(id);
		if (hideCard.isPresent()) {
			return ResponseEntity.ok(hideCard.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<HideCard>> get() {
		List<HideCard> hideCards = hdRepo.findAll();
		return ResponseEntity.ok(hideCards);
	}

	@Override
	public ResponseEntity<HideCard> add(HideCard entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		HideCard added = hdRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<HideCard> update(Long id, HideCard entity) {
		Optional<HideCard> exist = hdRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		HideCard updated = hdRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<HideCard> exist = hdRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = hdRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<HideCard> findByIDCardANDIDUser(Long idCard, Long idUser) {
		Optional<HideCard> exist = hdRepo.findByIDCardANDIDUser(idCard, idUser);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<Page<HideCard>> getListProductFromHideCardWithUser(Long idUser, Pageable pageable) {
		Optional<Page<HideCard>> exist = hdRepo.getListProductFromHideCardWithUser(idUser, pageable);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<HideCard>> loadAllHideCardWithUser(Long idUser) {
		Optional<List<HideCard>> exist = hdRepo.loadAllHideCardWithUser(idUser);
		return ResponseEntity.ok(exist.get());
	}

}
