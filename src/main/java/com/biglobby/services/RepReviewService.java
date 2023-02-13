package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.RepReview;

public interface RepReviewService {

    public ResponseEntity<List<RepReview>> getAll();

    public ResponseEntity<RepReview> getById(Long id);

    public ResponseEntity<RepReview> add(RepReview entity);

    public ResponseEntity<RepReview> update(Long id, RepReview entity);

    public ResponseEntity<Integer> deleteById(Long id);
}
