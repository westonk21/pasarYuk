package com.example.pasarYuk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "WHERE od.buyer_id=?1 AND od.order_status!='04' AND od.order_status!='05' "
			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	List<Order> findOngoingOrderWithBuyerId(Long buyerId);
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "WHERE od.buyer_id=?1 AND (od.order_status='04' OR od.order_status='05') "
			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	List<Order> findHistoryOrderWithBuyerId(Long buyerId);
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE pd.seller_id=?1 AND (od.order_status!='04' OR od.order_status!='05') "
			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	List<Order> findOngoingOrderWithSellerId(Long sellerId);
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE pd.seller_id=?1 AND (od.order_status='04' OR od.order_status='05') "
			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	List<Order> findHistoryOrderWithSellerId(Long sellerId);
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE pd.seller_id=?1 AND (od.order_status!='04' OR od.order_status!='05') "
			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	List<Order> findOngoingOrderWithStaffId(Long sellerId);
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE pd.seller_id=?1 AND (od.order_status='04' OR od.order_status='05') "
			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	List<Order> findHistoryOrderWithStaffId(Long sellerId);
	
	
	
	
	
	
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "WHERE od.buyer_id=?1 AND (od.order_status='01' OR od.order_status='02' OR od.order_status='03') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findOngoingOrderWithIdBuyer(Long id);
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE pd.seller_id=?1 AND (od.order_status='01' OR od.order_status='02' OR od.order_status='03') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findOngoingOrderWithIdSeller(Long id);
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
//			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
//			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE od.staff_id=?1 AND (od.order_status='01' OR od.order_status='02' OR od.order_status='03') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findOngoingOrderWithIdStaff(Long id);
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
//			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
//			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE od.staff_id=?1 AND od.order_type='PO' AND (od.order_status='01' OR od.order_status='02' OR od.order_status='03') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findPoOngoingOrderWithIdStaff(Long id);
	
	
	
	
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "WHERE od.buyer_id=?1 AND (od.order_status='04' OR od.order_status='05') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findHistoryOrderWithIdBuyer(Long id);
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE pd.seller_id=?1 AND (od.order_status='04' OR od.order_status='05') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findHistoryOrderWithIdSeller(Long id);
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
//			+ "INNER JOIN orderitem oi ON oi.order_id = od.order_id "
//			+ "INNER JOIN product pd ON oi.product_id = pd.product_id "
			+ "WHERE od.staff_id=?1 AND (od.order_status='04' OR od.order_status='05') "
			+ "ORDER BY od.order_timestamp ASC"
			, nativeQuery = true)
	List<Order> findHistoryOrderWithIdStaff(Long id);
	
	//yg di komen pake buat kalo staff nya udah di cari dlu , pas bikin order di assign staffid atao dimana
	@Query(value=
			"SELECT od.* "
			+ "FROM orders od "
//			+ "WHERE od.order_status='01' "
			+ "WHERE od.staff_id=?1 AND od.order_timestamp=?2 AND od.order_type=?3 AND (od.order_status='01' OR od.order_status='02' OR od.order_status='03')"
//			+ "ORDER BY od.order_date DESC, od.order_time DESC"
			, nativeQuery = true)
	Order findNewOrderWithIdStaff(Long id, String maxDate, String type);

	@Query(value=
			"SELECT MAX(order_timestamp) "
			+ "FROM orders "
			+ "WHERE staff_id=?1 AND order_type=?2"
			, nativeQuery = true)
	String findStaffLastOrderTimestamp(long staffId, String type);

	
	
	
	@Query(value=
			"select count(*) "
			+ "FROM orders "
			+ "WHERE order_status='01' OR order_status='02' OR order_status='03' "
			+ "GROUP BY order_status"
			, nativeQuery = true)
	List<Integer> getTotalOngoingOrder();
	@Query(value=
			"select count(*) "
			+ "FROM orders "
			+ "WHERE order_status='04' "
			+ "GROUP BY order_status"
			, nativeQuery = true)
	List<Integer> getTotalSuccesOrder();
	@Query(value=
			"select count(*) "
			+ "FROM orders "
			+ "WHERE order_status='05' "
			+ "GROUP BY order_status"
			, nativeQuery = true)
	List<Integer> getTotalCancelOrder();
	
	
	
	
	
	@Query(value=
			"select count(*) "
			+ "FROM orders "
			+ "WHERE (order_status='01' OR order_status='02' OR order_status='03') AND staff_id=?1 AND order_timestamp >= ?2 "
			+ "GROUP BY order_status"
			, nativeQuery = true)
	List<Integer> getTotalOngoingOrderWithStaffId(Long staffId, String timestamp);
	@Query(value=
			"select count(*) "
			+ "FROM orders "
			+ "WHERE order_status='04' AND staff_id=?1 AND order_timestamp >= ?2 "
			+ "GROUP BY order_status"
			, nativeQuery = true)
	Integer getTotalSuccessgOrderWithStaffId(Long staffId, String timestamp);
	@Query(value=
			"select count(*) "
			+ "FROM orders "
			+ "WHERE order_status='05' AND staff_id=?1 AND order_timestamp >= ?2 "
			+ "GROUP BY order_status"
			, nativeQuery = true)
	Integer getTotalCancelgOrderWithStaffId(Long staffId, String timestamp);
}
