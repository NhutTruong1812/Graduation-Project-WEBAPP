package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.RepReview;
import com.biglobby.entity.Review;
import com.biglobby.services.ReviewService;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        return reviewService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable("id") Long id) {
        return reviewService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Review> add(@RequestBody Review review) {
        return reviewService.add(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable("id") Long id, @RequestBody Review review) {
        return reviewService.update(id, review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable("id") Long id) {
        return reviewService.deleteById(id);
    }

    @GetMapping("/{id}/repreviews")
    public ResponseEntity<List<RepReview>> getRepReviews(@PathVariable("id") Long id) {
        return reviewService.getRepReviews(id);
    }

}
