package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
 
import com.biglobby.entity.Cart;
import com.biglobby.entity.OrderDetail;
import com.biglobby.entity.Product;
import com.biglobby.entity.ProductHistory;
import com.biglobby.entity.ReportCard;
import com.biglobby.repository.ProductHistoryRepository;
import com.biglobby.repository.ProductRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductHistoryRepository phRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<Product> get(Long id) {
		Optional<Product> pd = productRepo.findById(id);
		if (pd.isPresent()) {
			return ResponseEntity.ok(pd.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Product>> get() {
		List<Product> pds = productRepo.findAll();
		return ResponseEntity.ok(pds);
	}

	@Override
	public ResponseEntity<Product> add(Product entity) {
		Product added = productRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.PRODUCT + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Product> update(Long id, Product entity) {
		Optional<Product> exist = productRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Product updated = productRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.PRODUCT + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Product> exist = productRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = productRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.PRODUCT + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<List<ProductHistory>> getHistories(Long productId) {
		Optional<Product> product = productRepo.findById(productId);
		if (!product.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product.get().getProductHistories());
	}

	@Override
	public ResponseEntity<List<OrderDetail>> getOrderDetails(Long productId) {
		Optional<Product> product = productRepo.findById(productId);
		if (!product.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product.get().getOrderDetails());
	}

	@Override
	public ResponseEntity<List<Cart>> getCarts(Long productId) {
		Optional<Product> product = productRepo.findById(productId);
		if (!product.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product.get().getCarts());
	}

	@Override
	public ResponseEntity<ProductHistory> getHistory(Long phId) {
		Optional<ProductHistory> pdh = phRepo.findById(phId);
		if (pdh.isPresent()) {
			return ResponseEntity.ok(pdh.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<ProductHistory> addHistory(ProductHistory ph) {
		if (ph.getId() != null) {
			ph.setId(null);
		}
		ProductHistory added = phRepo.save(ph);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ProductHistory> updateHistory(Long phId, ProductHistory ph) {
		Optional<ProductHistory> exist = phRepo.findById(phId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		ph.setId(phId);
		ProductHistory updated = phRepo.save(ph);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteHistory(Long phId) {
		Optional<ProductHistory> exist = phRepo.findById(phId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = phRepo.removeById(phId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> getMaxPageProduct(boolean hidden) {
		Optional<Integer> pd = productRepo.getMaxPageProduct(hidden);
		return ResponseEntity.ok(pd.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListPage(boolean hidden, Pageable pageable) {
		Optional<Page<Product>> list = productRepo.findPage(hidden, pageable);
		return ResponseEntity.ok(list.get());
	}

	// <---by truongnvn
	@Override
	public ResponseEntity<Page<Product>> getListProductAllWhere(boolean isProduct, boolean hiden, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAllWHERE(isProduct, hiden, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAllWhereSearch(boolean isProduct, boolean hiden,
			String textSearh, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAllWHERESEARCH(isProduct, hiden, textSearh, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAll(boolean isProduct, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAll(isProduct, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAllSearch(boolean isProduct, String textSearh,
			Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAllSEARCH(isProduct, textSearh, pageable);
		return ResponseEntity.ok(products.get());
	}
	// --->

	@Override
	public ResponseEntity<Page<Product>> getListProductAllWhere(boolean isProduct, boolean hiden, Long status,
			Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAllWHERE(isProduct, hiden, status, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAllWhereSearch(boolean isProduct, boolean hiden, Long status,
			String textSearh, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAllWHERESEARCH(isProduct, hiden, status, textSearh,
				pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAll(boolean isProduct, Long status, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAll(isProduct, status, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAllSearch(boolean isProduct, Long status, String textSearh,
			Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListAllSEARCH(isProduct, status, textSearh, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListLoveCardAllWhereUser(boolean isProduct, boolean hiden, Long status,
			Long user, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListLoveCardAllWhereUser(isProduct, hiden, status, user,
				pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListLoveCardAllWhereUserSearch(boolean isProduct, boolean hiden,
			Long status, Long user, String textSearh, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Page<Product>> getListDemo(Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListDEMO(pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductWhereMyShopALL(boolean isProduct, boolean hiden, Long status,
			Long user, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListProductWhereMyShopALL(isProduct, hiden, status, user,
				pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductWhereMyShopALLSearch(boolean isProduct, boolean hiden,
			Long status, Long user, String textSearh, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListProductWhereMyShopALLSearch(isProduct, hiden, status,
				user, textSearh, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListSearchAndFilter(String nameProduct, Double from,
			Double to, String[] danhmuc, String[] location, Long user, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListProductSearchFilter(nameProduct, from, to, danhmuc,
				location, user, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListShopALL(boolean isProduct, boolean hiden, Long status, Long user,
			Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListProductShopALL(isProduct, hiden, status, user, pageable);
		return ResponseEntity.ok(products.get());
	}

	// fil sp của hang khác
	@Override
	public ResponseEntity<Page<Product>> getListMyShopSearchAndFilter(String nameProduct, Double to, Double from,
			String[] danhmuc, String[] location, Long user, Pageable pageable) {
		Optional<Page<Product>> products = productRepo.getListProductMyShopSearchFilter(nameProduct, to, from, danhmuc,
				location, user, pageable);
		return ResponseEntity.ok(products.get());
	}

	// admin sp bị reporrt
	@Override
	public ResponseEntity<Page<ReportCard>> getListproductReport(Long status, Pageable pageable) {
		Optional<Page<ReportCard>> products = productRepo.getListProductReportAll(status, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Page<ReportCard>> getListproductReportSeacrh(Long status, String text, Pageable pageable) {
		Optional<Page<ReportCard>> products = productRepo.getListProductReportAllSearch(status, text, pageable);
		return ResponseEntity.ok(products.get());
	}

	@Override
	public ResponseEntity<Product> getProductByCard(Long card) {
		Optional<Product> products = productRepo.getProductbyCard(card);
		return ResponseEntity.ok(products.get());
	}

	// phần của quang dùng cho quản lý sản phẩm
	@Override
	public ResponseEntity<Page<Product>> getListProductAllWhereUserStatus(Long id, Long idStatus, Pageable pageable) {
		Optional<Page<Product>> Product = productRepo.getListProductAllWhereUserStatus(id, idStatus, pageable);
		return ResponseEntity.ok(Product.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAllWhereUserStatusSearch(Long id, Long idStatus,
			String textSearch, Pageable pageable) {
		Optional<Page<Product>> Product = productRepo.getListProductAllWhereUserStatusSearch(id, idStatus, textSearch,
				pageable);
		return ResponseEntity.ok(Product.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductAllByIdUser(Long id, Pageable pageable) {
		Optional<Page<Product>> Product = productRepo.getListProductAllByIdUser(id, pageable);
		return ResponseEntity.ok(Product.get());
	}

	@Override
	public ResponseEntity<Page<Product>> getListProductCartAllByIdUserSearch(Long id, String textSearch,
			Pageable pageable) {
		Optional<Page<Product>> Product = productRepo.getListProductCartAllByIdUserSearch(id, textSearch, pageable);
		return ResponseEntity.ok(Product.get());
	}
	// end phần của quang

}
