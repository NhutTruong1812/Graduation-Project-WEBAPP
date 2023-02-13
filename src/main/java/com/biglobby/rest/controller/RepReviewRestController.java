package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.RepReview;
import com.biglobby.services.RepReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/repreviews")
@CrossOrigin("*")
public class RepReviewRestController {
    @Autowired
    private RepReviewService repService;

    @GetMapping
    public ResponseEntity<List<RepReview>> getAll() {
        return repService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepReview> getById(@PathVariable("id") Long id) {
        return repService.getById(id);
    }

    @PostMapping
    public ResponseEntity<RepReview> add(@RequestBody RepReview entity) {
        return repService.add(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepReview> update(@PathVariable("id") Long id, @RequestBody RepReview entity) {
        return repService.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteByid(@PathVariable("id") Long id) {
        return repService.deleteById(id);
    }

}
