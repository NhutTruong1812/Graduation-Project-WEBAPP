package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.RepReview;
import com.biglobby.repository.RepReviewRepository;
import com.biglobby.services.RepReviewService;

@Service
public class RepReviewServiceImpl implements RepReviewService {

    @Autowired
    private RepReviewRepository repRepo;

    @Override
    public ResponseEntity<List<RepReview>> getAll() {
        return ResponseEntity.ok(repRepo.findAll());
    }

    @Override
    public ResponseEntity<RepReview> getById(Long id) {
        Optional<RepReview> repReview = repRepo.findById(id);
        if (repReview.isPresent()) {
            return ResponseEntity.ok(repReview.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<RepReview> add(RepReview entity) {
        RepReview added = repRepo.save(entity);
        if (added != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(added);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<RepReview> update(Long id, RepReview entity) {
        Optional<RepReview> exist = repRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        RepReview updated = repRepo.save(entity);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Integer> deleteById(Long id) {
        Optional<RepReview> exist = repRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Integer deletedRow = repRepo.removeById(id);
        if (deletedRow != null && deletedRow > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
