package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.RepReview;
import com.biglobby.entity.Review;
import com.biglobby.repository.ReviewRepository; 
import com.biglobby.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Override
    public ResponseEntity<List<Review>> getAll() {
        return ResponseEntity.ok(reviewRepo.findAll());
    }

    @Override
    public ResponseEntity<Review> getById(Long id) {
        Optional<Review> review = reviewRepo.findById(id);
        if (review.isPresent()) {
            return ResponseEntity.ok(review.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Review> add(Review entity) {
        Review added = reviewRepo.save(entity);
        if (added != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(added);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Review> update(Long id, Review entity) {
        Optional<Review> exist = reviewRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        Review updated = reviewRepo.save(entity);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Integer> deleteById(Long id) {
        Optional<Review> exist = reviewRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Integer deletedRow = reviewRepo.removeById(id);
        if (deletedRow != null && deletedRow > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    public ResponseEntity<List<RepReview>> getRepReviews(Long id) {
        Optional<Review> review = reviewRepo.findById(id);
        if (!review.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review.get().getRepReviews());
    }

}
