package com.biglobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.MyBCoin;

@Repository
@Transactional
public interface MyBCoinRepository extends JpaRepository<MyBCoin, Long> {
	@Modifying
	@Query("DELETE FROM MyBCoin o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM MyBCoin o WHERE o.user.id=:userId")
	Optional<MyBCoin> findByUserId(@Param("userId") Long userId);
	
}
