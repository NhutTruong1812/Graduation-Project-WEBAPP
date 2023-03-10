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

import com.biglobby.entity.Product;
import com.biglobby.entity.ReportCard;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Modifying
	@Query("DELETE FROM Product o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o FROM Product o WHERE o.card.id=:cardId")
	Optional<Product> findByCardId(@Param("cardId") Long cardId);

	@Query("SELECT COUNT(o) FROM Product o WHERE o.card.hidden=:hidden")
	Optional<Integer> getMaxPageProduct(@Param("hidden") boolean hidden);

	@Query("SELECT o FROM Product o WHERE o.card.hidden=:hidden")
	Optional<Page<Product>> findPage(@Param("hidden") boolean hidden, Pageable pageable);

	// <---by truongnvn
	// Product ALL WHERE
	@Query("SELECT o FROM Product o WHERE (o.card.isProduct =:isProduct)")
	Optional<Page<Product>> getListAll(@Param("isProduct") boolean isProduct, Pageable pageable);

	// Product ALL WHERE SEARCH
	@Query("SELECT o FROM Product o WHERE o.card.isProduct =:isProduct AND o.product LIKE %:txt%")
	Optional<Page<Product>> getListAllSEARCH(@Param("isProduct") boolean isProduct, @Param("txt") String textSearch,
			Pageable pageable);

	// Product ALL WHERE
	@Query("SELECT o FROM Product o WHERE (o.card.isProduct =:isProduct and o.card.hidden=:hidden)")
	Optional<Page<Product>> getListAllWHERE(@Param("isProduct") boolean isProduct, @Param("hidden") boolean hidden,
			Pageable pageable);

	// Product ALL WHERE SEARCH
	@Query("SELECT o FROM Product o WHERE o.card.isProduct =:isProduct and o.card.hidden=:hidden AND o.product LIKE %:txt%")
	Optional<Page<Product>> getListAllWHERESEARCH(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("txt") String textSearch, Pageable pageable);
	// ----

	// Product ALL WHERE
	@Query("SELECT o FROM Product o WHERE (o.card.isProduct =:isProduct and o.card.status.id =:status)")
	Optional<Page<Product>> getListAll(@Param("isProduct") boolean isProduct, @Param("status") Long status,
			Pageable pageable);

	// Product ALL WHERE SEARCH
	@Query("SELECT o FROM Product o WHERE o.card.isProduct =:isProduct AND o.card.status.id =:status AND  o.product LIKE %:txt%")
	Optional<Page<Product>> getListAllSEARCH(@Param("isProduct") boolean isProduct, @Param("status") Long status,
			@Param("txt") String textSearch, Pageable pageable);

	// Product ALL WHERE
	@Query("SELECT o FROM Product o WHERE (o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status)")
	Optional<Page<Product>> getListAllWHERE(@Param("isProduct") boolean isProduct, @Param("hidden") boolean hidden,
			@Param("status") Long status, Pageable pageable);

	// Product ALL WHERE SEARCH
	@Query("SELECT o FROM Product o WHERE o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status AND o.product LIKE %:txt%")
	Optional<Page<Product>> getListAllWHERESEARCH(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("status") Long status, @Param("txt") String textSearch,
			Pageable pageable);

	// San Pham Yeu Thich
	// Product ALL WHERE
	@Query("SELECT o FROM Product o  join LoveCard l on o.card.id = l.card.id where (l.user.id =:user) and (o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status) ")
	Optional<Page<Product>> getListLoveCardAllWhereUser(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("status") Long status, @Param("user") Long user, Pageable pageable);

	// Product ALL WHERE SEARCH
	@Query("SELECT o FROM Product o  join LoveCard l on o.card.id = l.card.id where (l.user.id =:user) and (o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status) and o.product LIKE %:txt%")
	Optional<Page<Product>> getListLoveCardAllWhereUserSearch(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("status") Long status, @Param("txt") String textSearch,
			Pageable pageable);

	// S???n ph???m c???a h??ng kh??c
	/*
	 * select p.* from tbl_product p
	 * JOIN tbl_card c on p.id_card = c.id
	 * JOIn tbl_myshop s on s.id_user = c.id_user
	 * where
	 * s.id_user = 3
	 */
	// Product ALL WHERE
	@Query("SELECT o FROM Product o  JOIN MyShop s ON s.user.id = o.card.user.id where (s.user.id =:user) and (o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status) ")
	Optional<Page<Product>> getListProductWhereMyShopALL(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("status") Long status, @Param("user") Long user, Pageable pageable);

	// Product ALL WHERE SEARCH
	@Query("SELECT o FROM Product o  JOIN MyShop s ON s.user.id = o.card.user.id where (s.user.id =:user) and (o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status) and o.product LIKE %:txt%")
	Optional<Page<Product>> getListProductWhereMyShopALLSearch(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("status") Long status, @Param("user") Long user,
			@Param("txt") String textSearch, Pageable pageable);

	@Query(" SELECT o FROM Product o"
			// Li??n K???t
			+ " JOIN MyShop s ON o.card.user.id = s.user.id"
			// KHO???N ??I???U KI???N
			+ " WHERE "
			// ??i???u Ki???n b???t bu???c
			+ " ("
			// l?? s???n ph???m
			+ " o.card.isProduct ='true'"
			// ???????c hi???n
			+ " and o.card.hidden ='false'"
			// tr???ng th??i s???n s??ng
			+ " and o.card.status.id =3"
			// kh??ng b??? ng?????i ??ang xem ???n
			// + " and o.card.id NOT IN (SELECT h.card.id FROM HideCard h WHERE
			// h.user.id=:user)"
			+ " and s.user.id =:user"
			+ ")"
			+ " AND ("
			// <---??i???u ki???n c?? th??? c?? ho???c kh??ng
			// T??n S???n Ph???m
			+ " (o.product LIKE :key)"
			// Gi??
			+ " or (o.product.price BETWEEN :from AND :to)"
			// Danh m???c
			+ " or (o.category.category IN :danhmuc )"
			// V??? tr??
			+ " or (s.location IN :location)"
			// ??i???u ki???n c?? th??? c?? ho???c kh??ng--->
			+ " )")
	Optional<Page<Product>> getListProductMyShopSearchFilter(@Param("key") String nameProduct,
			@Param("from") Double from, @Param("to") Double to, @Param("danhmuc") String[] danhmuc,
			@Param("location") String[] location, @Param("user") Long user, Pageable pageable);

	@Query(" SELECT o FROM Product o"
			// Li??n K???t
			+ " JOIN MyShop s ON o.card.user.id = s.user.id"
			// KHO???N ??I???U KI???N
			+ " WHERE "
			// ??i???u Ki???n b???t bu???c
			+ " (o.card.isProduct ='true' and o.card.hidden ='false' and o.card.status.id =3)"
			+ " AND ("
			// <---??i???u ki???n c?? th??? c?? ho???c kh??ng
			// T??n S???n Ph???m
			+ " (o.product LIKE '%ZZZ%')"
			// Gi??
			+ " or (o.product.price BETWEEN 0 AND 5100)"
			// Danh m???c
			+ " or (o.category.category IN ('iii'))"
			// V??? tr??
			+ " or (s.location IN ('Ninh'))"
			// ??i???u ki???n c?? th??? c?? ho???c kh??ng--->
			+ " )")
	// @Query("SELECT o FROM Product o join LoveCard l on o.card.id = l.card.id
	// where (o.card.isProduct =true and o.card.hidden=false and o.card.status.id=3)
	// and l.user.id =4")
	Optional<Page<Product>> getListDEMO(Pageable pageable);
	/*
	 * select * from tbl_product p
	 * where p.id_card not in (select hi.id_card from tbl_hidecard hi WHERE id_card
	 * = 18)
	 */

	/*------------------------------------------------------------------------------------------------------*/
	@Query(" SELECT o FROM Product o"
			// Li??n K???t
			+ " JOIN MyShop s ON o.card.user.id = s.user.id"
			// KHO???N ??I???U KI???N
			+ " WHERE "
			// ??i???u Ki???n b???t bu???c
			+ " ("
			// l?? s???n ph???m
			+ " o.card.isProduct ='true'"
			// ???????c hi???n
			+ " and o.card.hidden ='false'"
			// tr???ng th??i s???n s??ng
			+ " and o.card.status.id =3"
			// kh??ng b??? ng?????i ??ang xem ???n
			+ " and o.card.id NOT IN (SELECT h.card.id FROM HideCard h WHERE h.user.id=:user)"
			+ ")"
			+ " AND ("
			// <---??i???u ki???n c?? th??? c?? ho???c kh??ng
			// T??n S???n Ph???m
			+ " (o.product LIKE :key)"
			// Gi??
			+ " or (o.product.price BETWEEN :from AND :to)"
			// Danh m???c
			+ " or (o.category.category IN :danhmuc )"
			// V??? tr??
			+ " or (s.location IN :location)"
			// ??i???u ki???n c?? th??? c?? ho???c kh??ng--->
			+ " )")
	Optional<Page<Product>> getListProductSearchFilter(@Param("key") String nameProduct, @Param("from") Double from,
			@Param("to") Double to, @Param("danhmuc") String[] danhmuc, @Param("location") String[] location,
			@Param("user") Long user, Pageable pageable);

	// C???a H??ng
	@Query("SELECT o FROM Product o  WHERE (o.card.isProduct =:isProduct and o.card.hidden=:hidden and o.card.status.id=:status  and o.card.id NOT IN (SELECT h.card.id FROM HideCard h WHERE h.user.id=:user))")
	Optional<Page<Product>> getListProductShopALL(@Param("isProduct") boolean isProduct,
			@Param("hidden") boolean hidden, @Param("status") Long status, @Param("user") Long user, Pageable pageable);

	// Product ALL WHERE SEARCH
	// @Query("SELECT o FROM Product o JOIN MyShop s ON s.user.id = o.card.user.id
	// where (s.user.id =:user) and (o.card.isProduct =:isProduct and
	// o.card.hidden=:hidden and o.card.status.id=:status) and o.product LIKE
	// %:txt%")
	// Optional<Page<Product>> getListProductSearchFilterSearch(@Param("isProduct")
	// boolean isProduct, @Param("hidden") boolean hidden,@Param("status") Long
	// status , @Param("user") Long user, @Param("txt") String textSearch, Pageable
	// pageable);
	//

	/*------------------------------------------------------------------------------------------------------*/

	// admin san pham bi bao cao
	@Query(" SELECT o FROM ReportCard o"
			+ " WHERE"
			+ "("
			+ " o.card.isProduct ='true'"
			+ " and o.status.id =:status"
			+ ")")
	Optional<Page<ReportCard>> getListProductReportAll(@Param("status") Long status, Pageable pageable);

	@Query(" SELECT o FROM ReportCard o"
			+ " WHERE"
			+ "("
			+ " o.card.isProduct ='true'"
			+ " and o.status.id =:status"
			+ ") "
			+ " and o.card.product.product LIKE %:txt%"
			+ "")
	Optional<Page<ReportCard>> getListProductReportAllSearch(@Param("status") Long status,
			@Param("txt") String textSearch, Pageable pageable);

	@Query(" SELECT o FROM Product o"
			+ " WHERE"
			+ " o.card.id =:card")
	Optional<Product> getProductbyCard(@Param("card") Long card);

	// --->

	/* Ph???n c???a quang d??ng cho qu???n l?? s???n ph???m */
	// <--- All USER
	@Query("SELECT o FROM Product o where o.card.user.id=:id AND o.card.isProduct=true")
	Optional<Page<Product>> getListProductAllByIdUser(@Param("id") Long id, Pageable pageable);

	// <--- All USER Search
	@Query("SELECT o FROM Product o where o.card.user.id=:id AND o.card.isProduct=true AND ((o.product LIKE %:txt%) or (o.category.category LIKE %:txt%))")
	Optional<Page<Product>> getListProductCartAllByIdUserSearch(@Param("id") Long id, @Param("txt") String textSearch,
			Pageable pageable);

	// <--- All WHERE USER
	@Query("SELECT o FROM Product o where o.card.status.id=:idStatus AND o.card.user.id=:id AND o.card.isProduct=true")
	Optional<Page<Product>> getListProductAllWhereUserStatus(@Param("id") Long id, @Param("idStatus") Long idStatus,
			Pageable pageable);

	// <--- All WHERE Search USER
	@Query("SELECT o FROM Product o where (o.card.status.id=:idStatus AND o.card.user.id=:id AND o.card.isProduct=true) AND ((o.product LIKE %:txt%) or (o.category.category LIKE %:txt%))")
	Optional<Page<Product>> getListProductAllWhereUserStatusSearch(@Param("id") Long id,
			@Param("idStatus") Long queryWhere, @Param("txt") String textSearch, Pageable pageable);
	/* end */
}
