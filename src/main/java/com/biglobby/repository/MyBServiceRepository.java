package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.MyBService;

@Repository
@Transactional
public interface MyBServiceRepository extends JpaRepository<MyBService, Long> {

	@Modifying
	@Query("DELETE FROM MyBService o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
}
