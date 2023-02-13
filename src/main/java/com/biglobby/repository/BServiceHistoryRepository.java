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

import com.biglobby.entity.BServiceHistory;

@Repository
@Transactional
public interface BServiceHistoryRepository extends JpaRepository<BServiceHistory, Long> {

	@Modifying
	@Query("DELETE FROM BServiceHistory o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	// phần của quang
		// dùng cho bservide history
		// <--- All
		@Query("SELECT o FROM BServiceHistory o")
		Optional<Page<BServiceHistory>> getListBServiceHistoryAll(Pageable pageable); 
	 
		//<--- All Search 
		@Query("SELECT o FROM BServiceHistory o where  ((o.user.fullname LIKE %:txt%) or (o.bservice.bservice LIKE %:txt%))")
		Optional<Page<BServiceHistory>> getListBServiceHistoryAllSearch(@Param("txt") String textSearch, Pageable pageable);
		
		// dùng cho phần đối tác
		// <--- All
		@Query("SELECT o FROM BServiceHistory o where o.user.id=:id")
		Optional<Page<BServiceHistory>> getListBServiceHistoryAllById(@Param("id") Long id, Pageable pageable); 
			 
		//<--- All Search 
		@Query("SELECT o FROM BServiceHistory o where o.user.id=:id and ((o.bservice.bservice LIKE %:txt%) or (o.actBy.fullname LIKE %:txt%) or (o.user.fullname LIKE %:txt%))")
		Optional<Page<BServiceHistory>> getListBServiceHistoryAllByIdSearch(@Param("id") Long id, @Param("txt") String textSearch, Pageable pageable);
		// end
}
