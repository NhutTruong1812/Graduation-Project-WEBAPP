package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.ReportCard;
import com.biglobby.repository.ReportCardRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.ReportCardService;

@Service
public class ReportCardServiceImpl implements ReportCardService {

	@Autowired
	private ReportCardRepository rcRepo;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<ReportCard> get(Long id) {
		Optional<ReportCard> rc = rcRepo.findById(id);
		if (rc.isPresent()) {
			return ResponseEntity.ok(rc.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<ReportCard>> get() {
		List<ReportCard> rcs = rcRepo.findAll();
		return ResponseEntity.ok(rcs);
	}

	@Override
	public ResponseEntity<ReportCard> add(ReportCard entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		ReportCard added = rcRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.REPORT_CARD + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ReportCard> update(Long id, ReportCard entity) {
		Optional<ReportCard> exist = rcRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		ReportCard updated = rcRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.REPORT_CARD + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<ReportCard> exist = rcRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = rcRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.REPORT_CARD + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// phần của hồ - admin - bài viết
	@Override
	public ResponseEntity<Page<ReportCard>> getListReportCardAll(Pageable pageable) {
		Optional<Page<ReportCard>> exist = rcRepo.getListReportCardAll(pageable);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<Page<ReportCard>> getListReportCardAllSearch(String textSearch, Pageable pageable) {
		Optional<Page<ReportCard>> exist = rcRepo.getListReportCardAllSearch(textSearch, pageable);
		return ResponseEntity.ok(exist.get());
	}
	// kết thúc phần của hồ - admin - bài viết

}
