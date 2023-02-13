package com.biglobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Modifying
	@Query("DELETE FROM Role o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
}
