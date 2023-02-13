package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.ReportCard;
import com.biglobby.services.ReportCardService;

@CrossOrigin
@RestController
@RequestMapping("/api/reports")
public class ReportCardRestController {
	@Autowired
	private ReportCardService reportService;

	@GetMapping
	public ResponseEntity<List<ReportCard>> get() {
		return reportService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReportCard> get(@PathVariable("id") Long id) {
		return reportService.get(id);
	}

	@PostMapping
	public ResponseEntity<ReportCard> add(@RequestBody ReportCard entity) {
		return reportService.add(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReportCard> update(@PathVariable("id") Long id, @RequestBody ReportCard entity) {
		return reportService.update(id, entity);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return reportService.delete(id);
	}

	// phần của hồ - admin - bài viết
	@GetMapping(params = { "page", "limit", "status", "key" })
	public ResponseEntity<Page<ReportCard>> getAdminReportCard(@RequestParam("page") int Npage, @RequestParam("limit") int Nitem,
			@RequestParam("status") int status, @RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);

		if (status < 0) {
			if (status == -1) {
				return reportService.getListReportCardAllSearch(textSearch, pageable);
			} else {
				return reportService.getListReportCardAllSearch(textSearch, pageable);
			}
		} else {
			if (status == 1) {
				return reportService.getListReportCardAll(pageable);
			} else {
				return reportService.getListReportCardAll(pageable);
			}
		}
	}
	// kết thúc phần của hồ - admin - bài viết
}
