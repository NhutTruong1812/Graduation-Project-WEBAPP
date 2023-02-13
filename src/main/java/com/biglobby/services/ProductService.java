package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Cart;
import com.biglobby.entity.OrderDetail;
import com.biglobby.entity.Product;
import com.biglobby.entity.ProductHistory;
import com.biglobby.entity.ReportCard;

public interface ProductService {

	public ResponseEntity<Product> get(Long id);

	public ResponseEntity<List<Product>> get();

	public ResponseEntity<Product> add(Product entity);

	public ResponseEntity<Product> update(Long id, Product entity);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<List<ProductHistory>> getHistories(Long productId);

	public ResponseEntity<ProductHistory> getHistory(Long phId);

	public ResponseEntity<ProductHistory> addHistory(ProductHistory ph);

	public ResponseEntity<ProductHistory> updateHistory(Long phId, ProductHistory ph);

	public ResponseEntity<Integer> deleteHistory(Long phId);

	public ResponseEntity<List<OrderDetail>> getOrderDetails(Long productId);

	public ResponseEntity<List<Cart>> getCarts(Long productId);

	public ResponseEntity<Integer> getMaxPageProduct(boolean hidden);

	public ResponseEntity<Page<Product>> getListPage(boolean hidden, Pageable pageable);

	// <---by truongnvn
	// ALL
	public ResponseEntity<Page<Product>> getListProductAll(boolean isProduct, Pageable pageable);

	// ALL SEARCH
	public ResponseEntity<Page<Product>> getListProductAllSearch(boolean isProduct, String textSearh,
			Pageable pageable);

	// ALL
	public ResponseEntity<Page<Product>> getListProductAll(boolean isProduct, Long status, Pageable pageable);

	// ALL SEARCH
	public ResponseEntity<Page<Product>> getListProductAllSearch(boolean isProduct, Long status, String textSearh,
			Pageable pageable);

	// ALL where
	public ResponseEntity<Page<Product>> getListProductAllWhere(boolean isProduct, boolean hiden, Pageable pageable);

	// ALL where SEARCH
	public ResponseEntity<Page<Product>> getListProductAllWhereSearch(boolean isProduct, boolean hiden,
			String textSearh, Pageable pageable);

	// ALL where
	public ResponseEntity<Page<Product>> getListProductAllWhere(boolean isProduct, boolean hiden, Long status,
			Pageable pageable);

	// ALL where SEARCH
	public ResponseEntity<Page<Product>> getListProductAllWhereSearch(boolean isProduct, boolean hiden, Long status,
			String textSearh, Pageable pageable);

	// ALL love card where
	public ResponseEntity<Page<Product>> getListLoveCardAllWhereUser(boolean isProduct, boolean hiden, Long status,
			Long user, Pageable pageable);

	// ALL love card where SEARCH
	public ResponseEntity<Page<Product>> getListLoveCardAllWhereUserSearch(boolean isProduct, boolean hiden,
			Long status, Long user, String textSearh, Pageable pageable);

	// SP Cửa hàng khác
	// ALL love card where
	public ResponseEntity<Page<Product>> getListProductWhereMyShopALL(boolean isProduct, boolean hiden, Long status,
			Long user, Pageable pageable);

	// ALL love card where SEARCH
	public ResponseEntity<Page<Product>> getListProductWhereMyShopALLSearch(boolean isProduct, boolean hiden,
			Long status, Long user, String textSearh, Pageable pageable);

	public ResponseEntity<Page<Product>> getListMyShopSearchAndFilter(String nameProduct, Double to, Double from,
			String[] danhmuc, String[] location, Long user, Pageable pageable);

	public ResponseEntity<Page<Product>> getListDemo(Pageable pageable);

	// của hàng all
	public ResponseEntity<Page<Product>> getListShopALL(boolean isProduct, boolean hiden, Long status, Long user,
			Pageable pageable);

	public ResponseEntity<Page<Product>> getListSearchAndFilter(String nameProduct, Double to, Double from,
			String[] danhmuc, String[] location, Long user, Pageable pageable);

	// admin get product report
	public ResponseEntity<Page<ReportCard>> getListproductReport(Long status, Pageable pageable);

	public ResponseEntity<Page<ReportCard>> getListproductReportSeacrh(Long status, String text, Pageable pageable);

	public ResponseEntity<Product> getProductByCard(Long card);
	// --->
	// --->

	// phần của quang dung cho quản lý sản phẩm
	// ALL WHERE USER
	public ResponseEntity<Page<Product>> getListProductAllWhereUserStatus(Long id, Long idStatus, Pageable pageable);

	// ALL WHERE SEARCH USER
	public ResponseEntity<Page<Product>> getListProductAllWhereUserStatusSearch(Long id, Long idStatus,
			String textSearch, Pageable pageable);

	// ALL USER
	public ResponseEntity<Page<Product>> getListProductAllByIdUser(Long id, Pageable pageable);

	// ALL USER SEARCH
	public ResponseEntity<Page<Product>> getListProductCartAllByIdUserSearch(Long id, String textSearch,
			Pageable pageable);
	// end phần của quang
}
