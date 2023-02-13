package com.biglobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.FailHistory;

@Repository
@Transactional
public interface FailHistoryRepository extends JpaRepository<FailHistory, Long> {

	@Modifying
	@Query("DELETE FROM FailHistory o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	@Query("SELECT o FROM FailHistory o WHERE o.card.id=:id")
	List<FailHistory> getByCardId(@Param("id") Long id);
}
