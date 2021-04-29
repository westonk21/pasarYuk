package com.example.pasarYuk.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.repository.BuyerRepository;

@Service
public class BuyerService {
	private BuyerRepository buyerRepository;
	
	@Autowired
	public BuyerService(BuyerRepository buyerRepository) {
		this.buyerRepository = buyerRepository;
	}
	
	public List<Buyer> listBuyer(){
		return buyerRepository.findAll();
	}
	
	public Buyer getBuyerById(Long buyerId) throws ResourceNotFoundException{
		Buyer buyer = buyerRepository.findById(buyerId)
				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
		return buyer;
	}
	
	public Buyer addNewBuyer(Buyer buyer) {
		return buyerRepository.save(buyer);
	}
	
	public Buyer updateBuyer(Long buyerId, Buyer buyerDetails) throws ResourceNotFoundException{
		Buyer buyer = buyerRepository.findById(buyerId)
				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
		
//		buyer.setUsername(buyerDetails.getUsername());
		buyer.setPassword(buyerDetails.getPassword());
		buyer.setEmail(buyerDetails.getEmail());
		buyer.setPhoneNumber(buyerDetails.getPhoneNumber());
		buyer.setAddress(buyerDetails.getAddress());
		buyer.setMarketId(buyerDetails.getMarketId());
		
		return this.buyerRepository.save(buyer);
	}
	
	public Map<String, Boolean> deleteBuyer(Long buyerId) throws ResourceNotFoundException{
		Buyer buyer = buyerRepository.findById(buyerId)
				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
		
		this.buyerRepository.delete(buyer);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
}
