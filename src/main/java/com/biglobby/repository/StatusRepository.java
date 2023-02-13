package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Status;

@Repository
@Transactional
public interface StatusRepository extends JpaRepository<Status, Long> {

	@Modifying
	@Query("DELETE FROM Status o WHERE o.id=:id")
	Integer removeByIs(@Param("id") Long id);
}
