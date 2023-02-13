package com.biglobby.services;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.BService;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.BServicePrice;
import com.biglobby.entity.MyBService;
import com.biglobby.entity.BServiceSubBanner;

public interface BServiceService {

	public ResponseEntity<BService> get(Long id);

	public ResponseEntity<List<BService>> get();

	public ResponseEntity<BService> add(BService bservice);

	public ResponseEntity<BService> update(Long id, BService bservice);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<MyBService>> getMyBServices(Long bServiceId);

	public ResponseEntity<List<BServiceHistory>> getHistories(Long bServiceId);

	public ResponseEntity<List<BServicePrice>> getPrices(Long bServiceId);

	public ResponseEntity<List<BServiceSubBanner>> getSubBanners(Long bServiceId);

	// phần của quang
	// ALL
	public ResponseEntity<List<Object[]>> getListBServiceAll();

	// ALL SEARCH
	public ResponseEntity<List<Object[]>> getListBServiceAllSearch(String textSearh);
	// end
}
