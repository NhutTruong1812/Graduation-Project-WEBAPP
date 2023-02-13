package com.biglobby.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.BServicePrice; 

@Repository
@Transactional
public interface BServicePriceRepository extends JpaRepository<BServicePrice, Long> {

	@Modifying
	@Query("DELETE FROM BServicePrice o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	@Query("SELECT o FROM BServicePrice o WHERE o.bservice.id=:id")
	List<BServicePrice> findByBserviceId(@Param("id") Long id);

}
