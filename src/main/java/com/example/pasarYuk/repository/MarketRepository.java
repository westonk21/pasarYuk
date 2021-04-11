package com.example.pasarYuk.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pasarYuk.model.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
	@Query(value=
			"SELECT mk.market_name "
			+ "FROM market mk "
			+ "WHERE mk.market_id=?1"
			, nativeQuery = true)
	String getMarketName(Long marketId);
}
