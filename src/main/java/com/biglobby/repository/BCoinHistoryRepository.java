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

import com.biglobby.entity.BCoinHistory; 

@Repository
@Transactional
public interface BCoinHistoryRepository extends JpaRepository<BCoinHistory, Long> {

	@Modifying
	@Query("DELETE FROM BCoinHistory o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	// phần của quang 
		// dùng cho phần lịch sử nạp tiền
		// <--- All
		@Query("SELECT o FROM BCoinHistory o")
		Optional<Page<BCoinHistory>> getListBCoinHistoryAll(Pageable pageable); 
						 
		//<--- All Search 
		@Query("SELECT o FROM BCoinHistory o where o.user.fullname LIKE %:txt%")
		Optional<Page<BCoinHistory>> getListBCoinHistoryAllSearch(@Param("txt") String textSearch, Pageable pageable);
	// end phần của quang 
}
