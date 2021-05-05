package com.example.pasarYuk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1"
			, nativeQuery = true)
	List<Product> findByMarket(Long marketId);
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 and pd.category=?2"
			, nativeQuery = true)
	List<Product> findByCategory(Long marketId, String category);
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 "
			+ "ORDER BY pd.price ASC"
			, nativeQuery = true)
	List<Product> findByFilterA(Long marketId);
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 "
			+ "ORDER BY pd.price DESC"
			, nativeQuery = true)
	List<Product> findByFilterB(Long marketId);
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 "
			+ "ORDER BY pd.avg_star DESC"
			, nativeQuery = true)
	List<Product> findByFilterC(Long marketId);
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 and pd.category=?2 "
			+ "ORDER BY pd.price ASC"
			, nativeQuery = true)
	List<Product> findByCategoryAndFilterA(Long marketId, String category);
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 and pd.category=?2 "
			+ "ORDER BY pd.price DESC"
			, nativeQuery = true)
	List<Product> findByCategoryAndFilterB(Long marketId, String category);
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE sl.market_id=?1 and pd.category=?2 "
			+ "ORDER BY pd.avg_star DESC"
			, nativeQuery = true)
	List<Product> findByCategoryAndFilterC(Long marketId, String category);
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN orderitem oi ON pd.product_id = oi.product_id "
			+ "WHERE oi.order_id=?1"
			, nativeQuery = true)
	List<Product> getListItemWithOrderId(Long orderId);
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN orderitem oi ON pd.product_id = oi.product_id "
			+ "WHERE oi.order_id=?1 "
			+ "ORDER BY pd.seller_id ASC"
			, nativeQuery = true)
	List<Product> getListItemWithOrderIdSortName(Long orderId);
	
	@Query(value=
			"SELECT pd.* "
			+ "FROM product pd "
			+ "INNER JOIN orderitem oi ON pd.product_id = oi.product_id "
			+ "WHERE oi.order_id=?1 AND pd.seller_id=?2"
			, nativeQuery = true)
	List<Product> getListItemWithOrderIdForSellerId(Long orderId, Long sellerId);	
	
	
	List<Product> findBySellerId(Long sellerId);

	Optional<Product> findByProductIdAndSellerId(Long productId, Long sellerId);
	
	@Query(value=
			"SELECT sl.market_id "
			+ "FROM product pd "
			+ "INNER JOIN seller sl ON pd.seller_id = sl.seller_id "
			+ "WHERE pd.product_id=?1"
			, nativeQuery = true)
	Long getMarketId(Long productId);
}
