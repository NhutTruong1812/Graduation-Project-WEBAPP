package com.biglobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.MyShop;

@Repository
@Transactional
public interface MyShopRepository extends JpaRepository<MyShop, Long> {

	@Modifying
	@Query("DELETE FROM MyShop o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM MyShop o WHERE o.user.id=:userId")
	Optional<MyShop> findByUserId(@Param("userId") Long userId);
	 
}
