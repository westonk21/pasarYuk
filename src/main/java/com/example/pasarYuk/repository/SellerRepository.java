package com.example.pasarYuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>{
	
	@Query(value=
			"SELECT count(sl.seller_id) "
			+ "FROM seller sl"
			, nativeQuery = true)
	int getTotalSeller();

	Seller findByEmail(String email);
}
