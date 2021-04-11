package com.example.pasarYuk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.repository.MarketRepository;

@Service
public class MarketService {

	
	private MarketRepository marketRepository;
	@Autowired
	public MarketService(MarketRepository marketRepository) {
		this.marketRepository = marketRepository;
	}
	
	public List<Market> listMarket(){
		return marketRepository.findAll();
	}
	
	public Market createNewMarket(Market market) {
		return marketRepository.save(market);
	}
}
