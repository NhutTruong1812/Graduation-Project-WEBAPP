package com.biglobby.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.CommentCard;

@Repository
@Transactional
public interface CommentCardRepository extends JpaRepository<CommentCard, Long> {

	@Modifying
	@Query("DELETE FROM CommentCard o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM CommentCard o WHERE o.card.id=:cardId")
	List<CommentCard> getCommentByCardId(@Param("cardId") Long cardId);

	@Query("SELECT o FROM CommentCard o WHERE o.card.id=:cardId")
	Page<CommentCard> getCommentByCardId(@Param("cardId") Long cardId, Pageable pageable);

	@Query("SELECT COUNT(o) FROM CommentCard o WHERE o.card.id=:cardId")
	Long getCountByCardId(@Param("cardId") Long cardId );

}
