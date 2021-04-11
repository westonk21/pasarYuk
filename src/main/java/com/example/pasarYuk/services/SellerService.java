package com.example.pasarYuk.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.repository.SellerRepository;

@Service
public class SellerService {
	private SellerRepository sellerRepository;
	
	@Autowired
	public SellerService(SellerRepository sellerRepository) {
		this.sellerRepository = sellerRepository;
	}
	
	public List<Seller> listSeller(){
		return sellerRepository.findAll();
	}
	
	public Seller getSellerById(Long sellerId) throws ResourceNotFoundException{
		Seller Seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
		return Seller;
	}
	
	public Seller addNewSeller(Seller seller) {
		return sellerRepository.save(seller);
	}
	
	public Seller updateSeller(Long sellerId, Seller sellerDetails) throws ResourceNotFoundException{
		Seller seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
		
		
		seller.setMarketId(sellerDetails.getMarketId());
		seller.setEmail(sellerDetails.getEmail());
		seller.setPhoneNumber(sellerDetails.getPhoneNumber());
		seller.setAddress(sellerDetails.getAddress());
		seller.setLapakName(sellerDetails.getLapakName());
		
		return this.sellerRepository.save(seller);
	}
	
	public Map<String, Boolean> deleteSeller(Long SellerId) throws ResourceNotFoundException{
		Seller Seller = sellerRepository.findById(SellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + SellerId));
		
		this.sellerRepository.delete(Seller);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
}
