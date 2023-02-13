package com.biglobby.repository;
 
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.biglobby.entity.News;
import com.biglobby.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	@Modifying
	@Query("DELETE FROM User o WHERE o.id=:id")
	Integer removeById(@Param("id") Long id);

	@Query("SELECT o.newsSents FROM User o WHERE o.id=:userId")
	List<News> getNewsSentByUserId(@Param("userId") Long userId);

	@Query("SELECT o.newsGots FROM User o WHERE o.id=:userId")
	List<News> getNewsGotByUserId(@Param("userId") Long userId);

	@Query("SELECT o FROM User o WHERE o.username=:username")
	public Optional<User> findByUsername(@Param("username") String username);

	@Query("SELECT o FROM User o WHERE o.username=:username")
	public User getByUserName(@Param("username") String username);

	@Query("SELECT o FROM User o WHERE o.email=:email")
	public Optional<User> findByEmail(@Param("email") String email);

	// Thống kê của Hồ
	// hiển thị số liệu khi load lần đầu
	@Query(nativeQuery = true, value = "exec usp_tkbanhang ?1, ?2, ?3")
	Optional<List<Object[]>> statistical(long id, String startDate, String endDate);

	// Đơn hàng: Hiển thị tất cả đơn hàng bao gồm: STT, id đơn hàng, Ngày tạo đơn,
	// Tổng tiền, Họ và tên người mua, Trạng thái
	@Query(nativeQuery = true, value = "select od.id_order, o.add_date, sum(od.quantity * od.price) as [sum], u.fullname, st.status from tbl_orderdetail od "
			+ "join tbl_order o on o.id = od.id_order " + "join tbl_user u on u.id = o.id_buyer "
			+ "join tbl_Status st on st.id = o.id_status " + "where o.id_saler = ?1 and o.add_date between ?2 and ?3 "
			+ "group by od.id_order, o.add_date, u.fullname, st.status ")
	Optional<List<Object[]>> statisticalDonHang(long id, String startDate, String endDate);

	// Đơn thành công: Hiển thị tất cả đơn hàng bao gồm: STT, id đơn hàng, Ngày tạo
	// đơn, Tổng tiền, Họ và tên người mua, Trạng thái (Tất cả trạng thái thành
	// công)
	@Query(nativeQuery = true, value = "select od.id_order, o.add_date, sum(od.quantity * od.price) as [sum], u.fullname, st.status from tbl_orderdetail od "
			+ "join tbl_order o on o.id = od.id_order " + "join tbl_user u on u.id = o.id_buyer "
			+ "join tbl_Status st on st.id = o.id_status "
			+ "where o.id_saler = ?1 and o.add_date between ?2 and ?3 and  o.id_status = 7 "
			+ "group by od.id_order, o.add_date, u.fullname, st.status ")
	Optional<List<Object[]>> statisticalDonThanhCong(long id, String startDate, String endDate);

	// Hàng bán được: STT, Id sản phẩm, Tên sản phẩm, Thể loại, Giá bán được, Số
	// lượng bán được (Sắp xếp sản phẩm bán được từ nhiều nhất tới ít nhất)
	@Query(nativeQuery = true, value = "select od.id_product, p.product, ca.category, od.price, sum(od.quantity) as [quantity2] from tbl_orderdetail od "
			+ "join tbl_product p on p.id = od.id_product " + "join tbl_category ca on ca.id = p.id_category "
			+ "join tbl_order o on o.id = od.id_order " + "where o.id_status = 7 and o.id_saler = ?1 and o.add_date between ?2 and ?3 "
			+ "group by od.id_product, p.product, ca.category, od.price " + "order by [quantity2] desc ")
	Optional<List<Object[]>> statisticalHangBanDuoc(long id, String startDate, String endDate);

	// Hàng tồn: Hàng tồn (hiện tổng - không theo mốc thời gian)
	@Query(nativeQuery = true, value = "select p.id, p.product, ca.category, p.price, st.status, iif(p.available is null, 0, p.available) as [available2] from tbl_product p "
			+ "join tbl_category ca on ca.id = p.id_category " + "join tbl_card cad on cad.id = p.id_card "
			+ "join tbl_Status st on st.id = cad.id_status " + "where cad.id_user = ?1 " + "order by available2 desc ")
	Optional<List<Object[]>> statisticalHangTon(long id);

	// Tổng khách hàng: STT, Họ tên khách hàng, tổng số sản phẩm mà khách đã mua
	// thời gian đó, , tổng tiền mà khách đã mua sản phẩm trong khoảng thời gian đó
	@Query(nativeQuery = true, value = "select u.fullname, sum(od.quantity) as [quantity2], sum(od.quantity * od.price) as [sum2]  from tbl_orderdetail od "
			+ "join tbl_order o on o.id = od.id_order " + "join tbl_user u on u.id = o.id_buyer "
			+ "where o.id_saler = ?1 and o.add_date between ?2 and ?3 " + "group by u.fullname order by sum2 desc ")
	Optional<List<Object[]>> statisticalTongKhachHang(long id, String startDate, String endDate);

	// Khách hàng quay lại: STT, Họ tên khách hàng, tổng số sản phẩm mà khách đã mua
	// thời gian đó, , tổng tiền mà khách đã mua sản phẩm trong khoảng thời gian đó
	@Query(nativeQuery = true, value = "select u.fullname, sum(od.quantity) as [quantity2], sum(od.quantity * od.price) as [sum2]  from tbl_orderdetail od "
			+ "join tbl_order o on o.id = od.id_order " + "join tbl_user u on u.id = o.id_buyer "
			+ "where o.id_saler = ?1 and o.add_date between ?2 and ?3 and u.id in (select o.id_buyer from tbl_order o where o.id_saler = ?1 and o.add_date < ?2) "
			+ "group by u.fullname order by sum2 desc ")
	Optional<List<Object[]>> statisticalKhachHangQuayLai(long id, String startDate, String endDate);

	// Khách hàng mới: STT, Họ tên khách hàng, tổng số sản phẩm mà khách đã mua thời
	// gian đó, , tổng tiền mà khách đã mua sản phẩm trong khoảng thời gian đó
	@Query(nativeQuery = true, value = "select u.fullname, sum(od.quantity) as [quantity2], sum(od.quantity * od.price) as [sum2]  from tbl_orderdetail od "
			+ "join tbl_order o on o.id = od.id_order " + "join tbl_user u on u.id = o.id_buyer "
			+ "where o.id_saler = ?1 and o.add_date between ?2 and ?3 and u.id not in (select o.id_buyer from tbl_order o where o.id_saler = ?1 and o.add_date < ?2) "
			+ "group by u.fullname order by sum2 desc ")
	Optional<List<Object[]>> statisticalKhachHangMoi(long id, String startDate, String endDate);

	// Theo dõi mới: STT, họ tên khách hàng, ngày theo dõi
	@Query(nativeQuery = true, value = "select u.fullname, fs.followDate from tbl_follow_shop fs "
			+ "join tbl_user u on u.id = fs.id_userFollow "
			+ "where fs.id_shop = ?1 and fs.followDate between ?2 and ?3 ")
	Optional<List<Object[]>> statisticalTheoDoiMoi(long id, String startDate, String endDate);
	
	// hiển thị số liệu khi load lần đầu
	@Query(nativeQuery = true, value = "exec usp_shopinfo ?1")
	Optional<List<Object[]>> statisticalInfoShop(long idUser);
	// Kết thúc thống kê của Hồ

}
