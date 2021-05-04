package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Market;
import com.example.pasarYuk.services.MarketService;

@CrossOrigin
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
	
	@PutMapping("/market/update/{marketId}")
	public ResponseEntity<Market> updateMarket(@PathVariable(value = "marketId") Long marketId, @RequestBody Market marketDetails) throws ResourceNotFoundException {
		Market market = marketService.updateMarket(marketId, marketDetails);
		return ResponseEntity.ok(market);
	}
	
	@DeleteMapping("/market/delete/{marketId}")
	public Map<String, Boolean> deleteMarket(@PathVariable(value = "marketId") Long marketId) throws ResourceNotFoundException{
		Map<String, Boolean> response = marketService.deleteMarket(marketId);
		return response;
	}
}
