package com.biglobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.CardSubBanner;

@Repository
@Transactional
public interface CardSubBannerRepository extends JpaRepository<CardSubBanner, Long> {

	@Modifying
	@Query("DELETE FROM CardSubBanner o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Modifying
	@Query("DELETE FROM CardSubBanner o WHERE o.card.id=:idCard")
	Integer removeByIdCard(@Param("idCard") Long idCard);

	@Query("SELECT o FROM CardSubBanner o where o.card.id =:idCard")
	Optional<List<CardSubBanner>> getListCardSubbannerbyCardId(@Param("idCard") Long idCard);

}
