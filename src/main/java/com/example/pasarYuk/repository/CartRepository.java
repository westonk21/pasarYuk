package com.example.pasarYuk.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Cart;
import com.example.pasarYuk.model.CartCkey;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartCkey>{

	//kalo gagal coba kasih and antara id nya
	public Cart findByCartId(CartCkey cartId);
	
	@Query(value = 
			"SELECT ct.* "
			+ "FROM cart ct "
//			+ "INNER JOIN market mk ON ct.market_id = mk.market_id "
			+ "WHERE ct.buyer_id=?1 "
			+ "ORDER BY ct.market_id ASC"
			, nativeQuery = true)
	public List<Cart> findByBuyerId(Long buyerId);
	
	@Query(value = 
			"SELECT ct.* "
			+ "FROM cart ct "
			+ "WHERE ct.buyer_id=?1 AND ct.market_id=?2"
			, nativeQuery = true)
	public List<Cart> findByBuyerIdAndMarketId(Long buyerId, Long marketId);
	
	@Query(value = 
			"SELECT ct.* "
			+ "FROM cart ct "
			+ "WHERE ct.buyer_id=?1 AND ct.checkItem='1'"
			, nativeQuery = true)
	public List<Cart> findCheckedItemByBuyerId(Long buyerId);
}
