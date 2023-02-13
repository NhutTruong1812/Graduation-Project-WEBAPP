package com.biglobby.repository;

import java.util.List;
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.BService; 

@Repository
@Transactional
public interface BServiceRepository extends JpaRepository<BService, Long> {

	@Modifying
	@Query("DELETE FROM BService o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "select bs.*, bsp1.price2 from tbl_bservice bs "
			+ "left outer join ("
			+ "	select bsp1.id_bservice, bsp1.price as [price2] from tbl_bservice_price bsp1 "
			+ "	left outer join ( "
			+ "		select max(bsp2.id) as idbsp2, bsp2.id_bservice, max(price_date) as [price_date2] from tbl_bservice_price bsp2 group by id_bservice) bsp2 on bsp2.id_bservice = bsp1.id_bservice "
			+ "		where bsp1.price_date = bsp2.price_date2 and bsp1.id = bsp2.idbsp2) bsp1 on bsp1.id_bservice = bs.id "
			+ "where bs.bservice like '%' and bs.block = 0 order by bsp1.price2 asc")
	List<Object[]> findPageBService();
	
	@Query("SELECT COUNT(o) FROM BService o")
	Optional<Integer> getMaxPageBService();
	
	// phần của quang
	// <--- All
	@Query(nativeQuery = true, value = "select bs.*, bsp1.price2 from tbl_bservice bs "
			+ "left outer join ("
			+ "	select bsp1.id_bservice, bsp1.price as [price2] from tbl_bservice_price bsp1 "
			+ "	left outer join ( "
			+ "		select max(bsp2.id) as idbsp2, bsp2.id_bservice, max(price_date) as [price_date2] from tbl_bservice_price bsp2 group by id_bservice) bsp2 on bsp2.id_bservice = bsp1.id_bservice "
			+ "		where bsp1.price_date = bsp2.price_date2 and bsp1.id = bsp2.idbsp2) bsp1 on bsp1.id_bservice = bs.id "
			+ "where bs.bservice like '%' and bs.blocked = 0 order by bsp1.price2 asc")
	Optional<List<Object[]>> getListBServiceAll(); 
 
	//<--- All Search 
	@Query(nativeQuery = true, value = "select bs.*, bsp1.price2 from tbl_bservice bs "
			+ "left outer join ("
			+ "	select bsp1.id_bservice, bsp1.price as [price2] from tbl_bservice_price bsp1 "
			+ "	left outer join ( "
			+ "		select max(bsp2.id) as idbsp2, bsp2.id_bservice, max(price_date) as [price_date2] from tbl_bservice_price bsp2 group by id_bservice) bsp2 on bsp2.id_bservice = bsp1.id_bservice "
			+ "		where bsp1.price_date = bsp2.price_date2 and bsp1.id = bsp2.idbsp2) bsp1 on bsp1.id_bservice = bs.id "
			+ "where bs.bservice like %:txt% and bs.blocked = 0 order by bsp1.price2 asc")
	Optional<List<Object[]>> getListBServiceAllSearch(@Param("txt") String textSearch); 
	// end
}
