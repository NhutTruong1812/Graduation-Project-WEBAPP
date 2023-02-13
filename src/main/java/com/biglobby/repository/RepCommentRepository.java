package com.biglobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.RepComment;

@Repository
@Transactional
public interface RepCommentRepository extends JpaRepository<RepComment, Long> {

	@Modifying
	@Query("DELETE FROM RepComment o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM RepComment o WHERE o.comment.id=:commentId")
	List<RepComment> findByCommentId(@Param("commentId") Long commentId);
}
