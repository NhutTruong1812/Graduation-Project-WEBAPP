package com.biglobby.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.DisplayFee;

@Repository
@Transactional
public interface DisplayFeeRepository extends JpaRepository<DisplayFee, Long> {

	@Modifying
	@Query("DELETE FROM DisplayFee o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM DisplayFee o WHERE :price >= o.priceFrom AND :price < o.priceTo")
	Optional<DisplayFee> findByPriceInRange(@Param("price") Double price);
}
