package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Action;

@Transactional
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

	@Query("DELETE FROM Action o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

}
