package com.example.pasarYuk.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Orderitem;
import com.example.pasarYuk.model.OrderitemCkey;

@Repository
public interface OrderitemRepository extends JpaRepository<Orderitem, OrderitemCkey>{

	@Query(value=
			"SELECT * "
			+ "FROM orderitem "
			+ "WHERE order_id=?1"
			, nativeQuery = true)
	List<Orderitem> findByOrderId(Long orderId);

	
}
