package com.biglobby.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biglobby.entity.Cart; 

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Modifying
	@Query("DELETE FROM Cart o WHERE o.id=:id")
	public Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM Cart o WHERE o.user.id=:userId AND o.product.card.id=:productId")
	public Optional<Cart> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

	@Query("SELECT o FROM Card o WHERE o.user.id=:userId")
	public List<Cart> findByUserId(@Param("userId") Long userId);

}
