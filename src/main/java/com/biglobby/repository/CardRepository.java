package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Card;

@Repository
@Transactional
public interface CardRepository extends JpaRepository<Card, Long> {

	@Modifying
	@Query("DELETE FROM Card o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	
}
