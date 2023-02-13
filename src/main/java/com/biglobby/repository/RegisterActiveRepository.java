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
 
import com.biglobby.entity.RegisterActive;

@Repository
@Transactional
public interface RegisterActiveRepository extends JpaRepository<RegisterActive, Long> {

	@Modifying
	@Query("DELETE FROM RegisterActive o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM RegisterActive o WHERE o.user.id=:userId")
	Optional<RegisterActive> findByUserId(@Param("userId") Long userId);
	
	// phần của quang
			// <--- lấy user từ Register Active
			@Query("SELECT o.user FROM RegisterActive o where o.user.blocked = false and o.actived = true")
			Optional<Page<RegisterActive>> getListUserAllToRegister(Pageable pageable); 
		 
			//<--- All Search 
			@Query("SELECT o.user FROM RegisterActive o where o.user.blocked = false and o.actived = true and (o.user.username LIKE %:txt% or o.user.fullname LIKE %:txt% or o.user.email LIKE %:txt%)")
			Optional<Page<RegisterActive>> getListUserAllSearchToRegister(@Param("txt") String textSearch, Pageable pageable); 
			// end
}
