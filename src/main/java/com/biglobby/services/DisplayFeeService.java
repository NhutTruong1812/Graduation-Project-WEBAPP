package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.DisplayFee;
import com.biglobby.entity.DisplayFeeHistory;

public interface DisplayFeeService {

	public ResponseEntity<DisplayFee> get(Long id);

	public ResponseEntity<List<DisplayFee>> get();
	
	public ResponseEntity<Page<DisplayFee>> getPage(Integer pagenum, Integer limit);

	public ResponseEntity<DisplayFee> add(DisplayFee displayFee);

	public ResponseEntity<DisplayFee> update(Long id, DisplayFee displayFee);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<DisplayFeeHistory>> getHistories(Long dfId);

	public ResponseEntity<DisplayFeeHistory> getHistory(Long hid);

	public ResponseEntity<DisplayFeeHistory> addHistory(DisplayFeeHistory history);

	public ResponseEntity<DisplayFeeHistory> updateHistory(Long hid, DisplayFeeHistory history);

	public ResponseEntity<Integer> deleteHistory(Long hid);

	public ResponseEntity<DisplayFee> findByPriceInRange(Double price);
}
