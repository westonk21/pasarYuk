package com.example.pasarYuk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.repository.MarketRepository;
import com.example.pasarYuk.services.MarketService;

@RestController
@RequestMapping("api/v1")
public class MarketController {

	@Autowired
	private MarketService marketService;
	
	@GetMapping("/market")
	public List<Market> getListMarket(){
		List<Market> temp = marketService.listMarket();
		return temp;
	}
	
	@PostMapping("/market/new")
	public Market newMarket(@RequestBody Market market) {
		Market marketResp = marketService.createNewMarket(market);
		return marketResp;
	}
}
