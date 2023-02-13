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

import com.biglobby.entity.LoveCard; 

@Repository
@Transactional
public interface LoveCardRepository extends JpaRepository<LoveCard, Long> {

	@Modifying
	@Query("DELETE FROM LoveCard o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM LoveCard o WHERE o.user.id=:userId AND o.card.id=:cardId")
	Optional<LoveCard> findByUserIdAndCardId(@Param("userId") Long userId, @Param("cardId") Long cardId);

	@Query("SELECT COUNT(o) FROM LoveCard o WHERE o.card.id=:cardId")
	Long getCountByCardId(@Param("cardId") Long cardId);
	
	// phần của quang 
	// dùng cho phần đối tác
	// <--- All
	@Query("SELECT o FROM LoveCard o where o.user.id=:id")
	Optional<Page<LoveCard>> getListLoveCardAllByIdUser(@Param("id") Long id, Pageable pageable); 
						 
	//<--- All Search 
	@Query("SELECT o FROM LoveCard o where o.user.id=:id and ((o.card.user.fullname LIKE %:txt%) or (o.user.fullname LIKE %:txt%))")
	Optional<Page<LoveCard>> getListLoveCardAllByIdUserSearch(@Param("id") Long id, @Param("txt") String textSearch, Pageable pageable);
	// end phần của quang 

}
