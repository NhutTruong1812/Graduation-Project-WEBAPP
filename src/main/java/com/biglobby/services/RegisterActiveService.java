package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.RegisterActive;

public interface RegisterActiveService {

	public ResponseEntity<RegisterActive> get(Long id);

	public ResponseEntity<List<RegisterActive>> get();

	public ResponseEntity<RegisterActive> getByUserId(Long userId);

	public ResponseEntity<RegisterActive> add(RegisterActive entity);

	public ResponseEntity<RegisterActive> update(Long id, RegisterActive entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<RegisterActive> getByIdUser(Long id);

	public ResponseEntity<RegisterActive> updateStatus(Long id);

	// phần của quang
	// ALL
	public ResponseEntity<Page<RegisterActive>> getListUserAll(Pageable pageable);

	// ALL SEARCH
	public ResponseEntity<Page<RegisterActive>> getListUserAllSearch(String textSearh, Pageable pageable);
	// end
}
