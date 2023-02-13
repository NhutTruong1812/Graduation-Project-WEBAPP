package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.ShareCard;

@Repository
@Transactional
public interface ShareCardRepository extends JpaRepository<ShareCard, Long> {

	@Modifying
	@Query("DELETE FROM ShareCard o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT COUNT(o) FROM ShareCard o WHERE o.id=:cardId")
	Long getCountByCardId(@Param("cardId") Long cardId);
}
