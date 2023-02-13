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

import com.biglobby.entity.Order;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Modifying
	@Query("DELETE FROM Order o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	/* Phần của quang */
	// 4. <--- All WHERE USER
	@Query("SELECT o FROM Order o where o.status.id=:idStatus AND o.buyer.id=:id")
	Optional<Page<Order>> getListOrderAllWhereUSER(@Param("id") Long id, @Param("idStatus") Long idStatus,
			Pageable pageable);

	// <--- All WHERE Search USER
	@Query("SELECT o FROM Order o where (o.status.id=:idStatus and o.buyer.id=:id) AND (o.saler.fullname LIKE %:txt%)")
	Optional<Page<Order>> getListOrderAllWhereUSERSearch(@Param("id") Long id, @Param("idStatus") Long queryWhere,
			@Param("txt") String textSearch, Pageable pageable);

	// 5. <--- All USER
	@Query("SELECT o FROM Order o where o.buyer.id=:id")
	Optional<Page<Order>> getListOrderAllUSER(@Param("id") Long id, Pageable pageable);

	// <--- All USER Search
	@Query("SELECT o FROM Order o where (o.buyer.id=:id) AND ((o.saler.fullname LIKE %:txt%) or (o.fullname LIKE %:txt%) or (o.buyer.fullname LIKE %:txt%))")
	Optional<Page<Order>> getListOrderAllUSERSearch(@Param("id") Long id, @Param("txt") String textSearch,
			Pageable pageable);

	/* end */

	@Query("SELECT COUNT(o) FROM Order o WHERE o.saler.id=:id")
	Optional<Integer> getMaxPageOrderSaler(@Param("id") Long id);

	@Query("SELECT o FROM Order o WHERE o.saler.id=:id")
	Optional<Page<Order>> findPageSaler(@Param("id") Long id, Pageable pageable);

	// <-------------------------------------------------------------------------------------------------------------------------
	// 1. <--- All
	@Query("SELECT o FROM Order o")
	Optional<Page<Order>> getListOrderAll(Pageable pageable);

	// <--- All Search
	@Query("SELECT o FROM Order o where ((o.fullname LIKE %:txt%) OR (o.phonenum LIKE %:txt%) or (o.address LIKE %:txt%))")
	Optional<Page<Order>> getListOrderAllSearch(@Param("txt") String textSearch, Pageable pageable);

	// 2. <--- All WHERE
	@Query("SELECT o FROM Order o where o.status.id=:idStatus")
	Optional<Page<Order>> getListOrderAllWhere(@Param("idStatus") Long idStatus, Pageable pageable);

	// <--- All WHERE Search
	@Query("SELECT o FROM Order o where (o.status.id=:idStatus) AND ((o.fullname LIKE %:txt%) OR (o.phonenum LIKE %:txt%) or (o.address LIKE %:txt%))")
	Optional<Page<Order>> getListOrderAllWhereSearch(@Param("idStatus") Long queryWhere,
			@Param("txt") String textSearch, Pageable pageable);

	// 3. <--- All WHERE OR
	@Query("SELECT o FROM Order o where (o.status.id=:idStatus1 or o.status.id=:idStatus2 )")
	Optional<Page<Order>> getListOrderAllWhereOr(@Param("idStatus1") Long idStatus1, @Param("idStatus2") Long idStatus2,
			Pageable pageable);

	// <--- All WHERE OR Search
	@Query("SELECT o FROM Order o  where (o.status.id=:idStatus1 or o.status.id=:idStatus2 ) AND ((o.fullname LIKE %:txt%) OR (o.phonenum LIKE %:txt%) or (o.address LIKE %:txt%))")
	Optional<Page<Order>> getListOrderAllWhereOrSearch(@Param("idStatus1") Long idStatus1,
			@Param("idStatus2") Long idStatus2, @Param("txt") String textSearch, Pageable pageable);

	// ------------------------------------------------------------------------------------------------------------------------->
	// All where
	@Query("SELECT o FROM Order o Where o.status.id=:queryWhere")
	Optional<List<Order>> getListOrderWhere(@Param("queryWhere") Long queryWhere);

	@Query("SELECT COUNT(o) FROM Order o Where o.status.id=:queryWhere")
	Optional<Integer> getMaxPageOrderWhere(@Param("queryWhere") Long queryWhere);

	/* Phần của Hồ */
	// 4. <--- All WHERE USER
	@Query("SELECT o FROM Order o where o.status.id=:idStatus AND o.saler.id=:id")
	Optional<Page<Order>> getListOrderAllWhereUSERSaler(@Param("id") Long id, @Param("idStatus") Long idStatus,
			Pageable pageable);

	// <--- All WHERE Search USER
	@Query("SELECT o FROM Order o where (o.status.id=:idStatus and o.saler.id=:id) AND (o.buyer.fullname LIKE %:txt%)")
	Optional<Page<Order>> getListOrderAllWhereUSERSearchSaler(@Param("id") Long id, @Param("idStatus") Long queryWhere,
			@Param("txt") String textSearch, Pageable pageable);

	// 5. <--- All USER
	@Query("SELECT o FROM Order o where o.saler.id=:id")
	Optional<Page<Order>> getListOrderAllUSERSaler(@Param("id") Long id, Pageable pageable);

	// <--- All USER Search
	@Query("SELECT o FROM Order o where (o.saler.id=:id) AND (o.buyer.fullname LIKE %:txt%)")
	Optional<Page<Order>> getListOrderAllUSERSearchSaler(@Param("id") Long id, @Param("txt") String textSearch,
			Pageable pageable);

}
