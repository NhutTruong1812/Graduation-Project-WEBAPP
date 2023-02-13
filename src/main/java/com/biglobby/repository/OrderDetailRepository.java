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

import com.biglobby.entity.OrderDetail;

@Repository
@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	@Modifying
	@Query("DELETE FROM OrderDetail o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);
	
	@Query("select SUM(o.price * o.quantity) FROM OrderDetail o where o.order.id = ?1")
	Double sumPriceOrder(Long id);
	
	// dùng cho phần đối tác
	// <--- All
	@Query("SELECT o FROM OrderDetail o where ((o.order.buyer.id=:id) or (o.order.saler.id=:id))")
	Optional<Page<OrderDetail>> getListOrderDetailAllById(@Param("id") Long id, Pageable pageable); 
				 
	//<--- All Search 
	@Query("SELECT o FROM OrderDetail o where ((o.order.buyer.id=:id) or (o.order.saler.id=:id)) and ((o.order.saler.fullname LIKE %:txt%) or (o.order.buyer.fullname LIKE %:txt%) or (o.product.product LIKE %:txt%) or (o.product.category.category LIKE %:txt%))")
	Optional<Page<OrderDetail>> getListOrderDetailAllByIdSearch(@Param("id") Long id, @Param("txt") String textSearch, Pageable pageable);

	//truongnvn
	// select SUM(od.quantity) from tbl_order o join tbl_orderdetail od on o.id = od.id_order where o.id_saler = 10155 and o.id_status = 7
	@Query("SELECT SUM(o.quantity) FROM OrderDetail o where (o.order.saler.id=:user) and (o.order.status.id =:status)")
	Optional<Long> getSumQuantity(@Param("user") Long user, @Param("status") Long status); 
	
	@Query("SELECT o FROM OrderDetail o where o.order.id=:orderid")
	Optional<List<OrderDetail>> getOrderDetailByIdOrder(@Param("orderid") Long orderId); 
}
