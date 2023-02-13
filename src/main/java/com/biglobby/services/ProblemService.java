package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Problem;
import com.biglobby.entity.ReportCard;

public interface ProblemService {

	public ResponseEntity<Problem> get(Long id);

	public ResponseEntity<List<Problem>> get();

	public ResponseEntity<Problem> add(Problem entity);

	public ResponseEntity<Problem> update(Long id, Problem entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<ReportCard>> getReportCards(Long pbId);
}
