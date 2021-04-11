package com.example.pasarYuk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long>{
	
	@Query(value="SELECT * FROM buyer WHERE email=?1", nativeQuery = true)
	List<Buyer> testQuery(String email);
}
