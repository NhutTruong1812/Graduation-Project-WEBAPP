package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Review;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Query("DELETE FROM Review o WHERE o.id=:id")
    Integer removeById(@Param("id") Long id);

}
