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

import com.biglobby.entity.ReportCard;

@Repository
@Transactional
public interface ReportCardRepository extends JpaRepository<ReportCard, Long> {

	@Modifying
	@Query("DELETE FROM ReportCard o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Modifying
	@Query("UPDATE ReportCard o"
			+ " SET o.status.id=:status"
			+ " WHERE o.id=:id")
	Integer updateById(@Param("id") Long id, @Param("status") Long status);

	// phần của hồ - admin - bài viết
	// Select all bài viết bị báo cáo và đang chờ duyệt
	@Query("SELECT rpc FROM ReportCard rpc WHERE rpc.status = 12 AND rpc.card.isProduct = false")
	Optional<Page<ReportCard>> getListReportCardAll(Pageable pageable);

	// <--- All Search theo tiêu đề bài post hoặc tên người report
	@Query("SELECT rpc FROM ReportCard rpc where rpc.status = 12 and rpc.card.isProduct = false and ((rpc.card.post.title LIKE %:txt%) OR (rpc.user.fullname LIKE %:txt%))")
	Optional<Page<ReportCard>> getListReportCardAllSearch(@Param("txt") String textSearch, Pageable pageable);
	// kết thúc phần của hồ - admin - bài viết
}
