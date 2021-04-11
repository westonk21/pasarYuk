package com.example.pasarYuk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>{
	
	
}
