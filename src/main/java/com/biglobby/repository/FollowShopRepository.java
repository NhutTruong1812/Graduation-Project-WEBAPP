package com.biglobby.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.FollowShop;

@Repository
@Transactional
public interface FollowShopRepository extends JpaRepository<FollowShop, Long> {
	@Modifying
	@Query("DELETE FROM FollowShop o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	@Query("SELECT o FROM FollowShop o WHERE o.shop.user.id=:user")
	Optional<Page<FollowShop>> getListFollowShopALL(@Param("user") Long user, Pageable pageable);
	  
	@Query("SELECT o FROM FollowShop o WHERE o.shop.id=:idShop")
	Optional<Page<FollowShop>> getListFollowShopByIDShop(@Param("idShop") Long idShop, Pageable pageable);
}
