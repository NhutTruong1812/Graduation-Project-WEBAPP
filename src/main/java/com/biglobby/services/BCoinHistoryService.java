package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.BCoinHistory; 

public interface BCoinHistoryService {

	public ResponseEntity<BCoinHistory> get(Long id);

	public ResponseEntity<List<BCoinHistory>> get();

	public ResponseEntity<BCoinHistory> add(BCoinHistory bcoinHistory);

	public ResponseEntity<BCoinHistory> update(Long id, BCoinHistory bcoinHistory);

	public ResponseEntity<Integer> delete(Long id);
	
	// phần của quang
	// dùng cho phần lịch sử nập tiền
		//ALL  
		public ResponseEntity<Page<BCoinHistory>> getListBCoinHistoryAll(Pageable pageable);
		
		//ALL SEARCH 
		public ResponseEntity<Page<BCoinHistory>> getListBCoinHistoryAllSearch(String textSearh, Pageable pageable);
	// end phần quang
}
