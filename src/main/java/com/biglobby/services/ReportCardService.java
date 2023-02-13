package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.ReportCard;

public interface ReportCardService {

	public ResponseEntity<ReportCard> get(Long id);

	public ResponseEntity<List<ReportCard>> get();

	public ResponseEntity<ReportCard> add(ReportCard entity);

	public ResponseEntity<ReportCard> update(Long id, ReportCard entity);

	public ResponseEntity<Integer> delete(Long id);

	// phần của hồ - admin - bài viết
	public ResponseEntity<Page<ReportCard>> getListReportCardAll(Pageable pageable);

	public ResponseEntity<Page<ReportCard>> getListReportCardAllSearch(String textSearch, Pageable pageable);

	// kết thúc phần của hồ - admin - bài viết
}
