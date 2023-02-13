package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.BServiceHistory; 

public interface BServiceHistoryService {

	public ResponseEntity<BServiceHistory> get(Long id);

	public ResponseEntity<List<BServiceHistory>> get();

	public ResponseEntity<BServiceHistory> add(BServiceHistory bserviceHistory);

	public ResponseEntity<BServiceHistory> update(Long id, BServiceHistory bserviceHistory);

	public ResponseEntity<Integer> delete(Long id);
	
	// phần của quang
		//ALL  
		public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAll(Pageable pageable);
			
		//ALL SEARCH 
		public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAllSearch(String textSearh, Pageable pageable);
		
		//ALL  
		public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAllById(Long id, Pageable pageable);
					
		//ALL SEARCH 
		public ResponseEntity<Page<BServiceHistory>> getListBServiceHistoryAllByIdSearch(Long id, String textSearh, Pageable pageable);
		// end
}
