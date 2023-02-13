package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.RepReview;

@Transactional
public interface RepReviewRepository extends JpaRepository<RepReview, Long> {

    @Modifying
    @Query("DELETE FROM RepReview o WHERE o.id=:id")
    Integer removeById(@Param("id") Long id);

}
