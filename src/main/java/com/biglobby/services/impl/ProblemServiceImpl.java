package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Problem;
import com.biglobby.entity.ReportCard;
import com.biglobby.repository.ProblemRepository;
import com.biglobby.services.ProblemService;

@Service
public class ProblemServiceImpl implements ProblemService {
	@Autowired
	private ProblemRepository pbRepo;

	@Override
	public ResponseEntity<Problem> get(Long id) {
		Optional<Problem> pb = pbRepo.findById(id);
		if (pb.isPresent()) {
			return ResponseEntity.ok(pb.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Problem>> get() {
		List<Problem> pbs = pbRepo.findAll();
		return ResponseEntity.ok(pbs);
	}

	@Override
	public ResponseEntity<Problem> add(Problem entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		Problem added = pbRepo.save(entity);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Problem> update(Long id, Problem entity) {
		Optional<Problem> exist = pbRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		Problem updated = pbRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Problem> exist = pbRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = pbRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<ReportCard>> getReportCards(Long pbId) {
		Optional<Problem> problem = pbRepo.findById(pbId);
		if (!problem.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(problem.get().getReportCards());
	}

}
