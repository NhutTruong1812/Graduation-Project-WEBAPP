package com.biglobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.HideCard;

@Repository
@Transactional
public interface HideCardRepository extends JpaRepository<HideCard, Long> {

	@Modifying
	@Query("DELETE FROM HideCard o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("Select o FROM HideCard o WHERE o.card.id=:idCard AND o.user.id=:idUser")
	Optional<HideCard> findByIDCardANDIDUser(@Param("idCard") Long idCard, @Param("idUser") Long idUser);

	@Query("SELECT o FROM HideCard o where o.user.id=:idUser and o.card.isProduct=false")
	Optional<Page<HideCard>> getListProductFromHideCardWithUser(@Param("idUser") Long idUser, Pageable pageable);
	
	@Query("SELECT o FROM HideCard o where o.user.id=:idUser")
	Optional<List<HideCard>> loadAllHideCardWithUser(@Param("idUser") Long idUser);
}
