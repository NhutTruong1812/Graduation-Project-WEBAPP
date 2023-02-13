package com.biglobby.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.entity.RepReview;
import com.biglobby.entity.Review;

public interface ReviewService {

    public ResponseEntity<List<Review>> getAll();

    public ResponseEntity<Review> getById(Long id);

    public ResponseEntity<Review> add(Review entity);

    public ResponseEntity<Review> update(Long id, Review entity);

    public ResponseEntity<Integer> deleteById(Long id);

    public ResponseEntity<List<RepReview>> getRepReviews(Long id);
}
