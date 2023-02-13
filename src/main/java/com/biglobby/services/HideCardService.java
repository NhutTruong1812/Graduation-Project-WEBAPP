package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.HideCard;

public interface HideCardService {

	public ResponseEntity<HideCard> get(Long id);

	public ResponseEntity<List<HideCard>> get();

	public ResponseEntity<HideCard> add(HideCard entity);

	public ResponseEntity<HideCard> update(Long id, HideCard entity);

	public ResponseEntity<Integer> delete(Long id);
	
	public ResponseEntity<HideCard> findByIDCardANDIDUser(Long idCard, Long idUser);
	
	public ResponseEntity<Page<HideCard>> getListProductFromHideCardWithUser(Long idUser, Pageable pageable);
	
	public ResponseEntity<List<HideCard>> loadAllHideCardWithUser(Long idUser);

}
