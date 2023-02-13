package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.DisplayFeeHistory;

@Repository
@Transactional
public interface DisplayFeeHistoryRepository extends JpaRepository<DisplayFeeHistory, Long> {

	@Modifying
	@Query("DELETE FROM DisplayFeeHistory o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);


}
