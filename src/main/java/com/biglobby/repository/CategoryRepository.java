package com.biglobby.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.Category; 

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Modifying
	@Query("DELETE FROM Category o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	//<---by truongnvn
	
	//Category ALL STATUS SEARCH
	@Query("SELECT o FROM Category o Where o.status.id=:status")
	Optional<Page<Category>> getListCategoryAllWhereStatus(@Param("status") Long status,Pageable pageable);

	//Category ALL STATUS SEARCH
	@Query("SELECT o FROM Category o WHERE  o.status.id =:status and o.category LIKE %:txt%")
	Optional<Page<Category>> getListCategoryAllWhereStatusSearch(@Param("status") Long status, @Param("txt") String textSearch, Pageable pageable);
	
	//Category ALL STATUS SEARCH nopage
	@Query("SELECT o FROM Category o Where o.status.id =:status")
	Optional<List<Category>> getListCategoryAllWhereStatus(@Param("status") Long status);

	//Category ALL STATUS SEARCH nopage
	@Query("SELECT o FROM Category o WHERE  o.status.id =:status and o.category LIKE %:txt%")
	Optional<List<Category>> getListCategoryAllWhereStatusSearch(@Param("status") Long status, @Param("txt") String textSearch);
	
	//Category ALL WHERE
	@Query("SELECT o FROM Category o WHERE o.blocked=:block AND o.status.id=:status")
	Optional<Page<Category>> getListCategoryAllWhereBlockAndStatus(@Param("block") boolean block, @Param("status") Long status, Pageable pageable);

	//Category ALL WHERE SEARCH
	@Query("SELECT o FROM Category o WHERE (o.blocked=:block AND o.status.id=:status) and  o.category LIKE %:txt%")
	Optional<Page<Category>> getListCategoryAllWhereBlockAndStatusSearch(@Param("block") boolean block, @Param("status") Long status, @Param("txt") String textSearch, Pageable pageable);
	//--->
	// phần của quang
	// <--- All
	@Query("SELECT c FROM Category c WHERE c.status.id=1")
	Optional<Page<Category>> getListCategoryStatus(Pageable pageable);
	
	@Query("SELECT c FROM Category c WHERE c.status.id=3")
	Optional<Page<Category>> getListCategoryAll(Pageable pageable); 
 
	//<--- All Search 
	@Query("SELECT c FROM Category c where c.status.id=3 and (c.category LIKE %:txt%)")
	Optional<Page<Category>> getListCategoryAllSearch(@Param("txt") String textSearch, Pageable pageable); 
	
	@Query("SELECT c FROM Category c where c.status.id=1 and ((c.category LIKE %:txt%) or (c.addBy.fullname LIKE %:txt%))")
	Optional<Page<Category>> getListCategoryStatusSearch(@Param("txt") String textSearch, Pageable pageable); 
	
	@Query("SELECT c FROM Category c WHERE c.status.id=3")
	List<Category> getListCategoryStatus3(); 
	// end
	
	
}
