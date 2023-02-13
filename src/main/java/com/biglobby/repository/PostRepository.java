package com.biglobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Post;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

	@Modifying
	@Query("DELETE FROM Post o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM Post o WHERE o.card.id=:cardId")
	Optional<Post> findByCardId(@Param("cardId") Long cardId);

	// phần của hồ - admin - bài viết
	// Select all bài viết
	@Query("SELECT p FROM Post p")
	Optional<Page<Post>> getListPostAll(Pageable pageable);

	// <--- All Search
	@Query("SELECT p FROM Post p where (p.title LIKE %:txt%) OR (p.card.user.fullname LIKE %:txt%)")
	Optional<Page<Post>> getListPostAllSearch(@Param("txt") String textSearch, Pageable pageable);

	// <--- Status filter
	@Query("SELECT p FROM Post p where p.card.status.id=:idStatus")
	Optional<Page<Post>> getListPostAllFilterByStatusCard(@Param("idStatus") Long idStatus, Pageable pageable);

	// <--- Status filter and search
	@Query("SELECT p FROM Post p where (p.card.status.id=:idStatus AND p.title LIKE %:txt%) or (p.card.status.id=:idStatus AND p.card.user.fullname LIKE %:txt%)")
	Optional<Page<Post>> getListPostAllFilterAndSearch(@Param("idStatus") Long idStatus,
			@Param("txt") String textSearch, Pageable pageable);
	// kết thúc phần của hồ - admin - bài viết

	// phần của quang
	// dùng cho phần đối tác
	// <--- All
	@Query("SELECT p FROM Post p where p.card.user.id=:id")
	Optional<Page<Post>> getListPostAllByIdUser(@Param("id") Long id, Pageable pageable);

	// <--- All Search
	@Query("SELECT p FROM Post p where p.card.user.id=:id and ((p.title LIKE %:txt%) or (p.card.user.fullname LIKE %:txt%))")
	Optional<Page<Post>> getListPostAllByIdUserSearch(@Param("id") Long id, @Param("txt") String textSearch,
			Pageable pageable);
	// end phần của quang
	
	@Query("SELECT o FROM Post o where o.card.id=:idCard")
	Optional<Page<Post>> getListPostFromHideCardWithIdCard(@Param("idCard") Long idCard, Pageable pageable);
	
	@Query("SELECT o FROM Post o WHERE o.card.isProduct=false and o.card.status.id=3 and o.card.hidden=false ORDER BY o.card.postAt DESC")
	Optional<List<Post>> loadAllPost();
	
	@Query("SELECT o FROM Post o WHERE o.card.isProduct=false and o.card.status.id=3 and o.card.hidden=false and o.card.user.id=:idUser ORDER BY o.card.postAt DESC")
	Optional<List<Post>> loadAllPostOfUser(@Param("idUser") Long idUser);
	
}
