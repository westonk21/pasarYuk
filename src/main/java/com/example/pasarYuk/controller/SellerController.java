package com.example.pasarYuk.controller;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Seller;
//import com.example.pasarYuk.repository.sellerRepository;
import com.example.pasarYuk.services.SellerService;

@RestController
@RequestMapping("/api/v1")
public class SellerController {

	@Autowired
	private SellerService sellerService;
	//private sellerRepository sellerRepository;
	
	//get Seller
	@GetMapping("sellers")
	public List<Seller> getAllSeller(){
		//return this.sellerRepository.findAll();
		//List<Seller> Seller = (List<Seller>) sellerRepository.testQuery("weto@gmail.com");
		//Seller.forEach(System.out::println);
		//return this.sellerRepository.findAll();
		return sellerService.listSeller();
	}
	
	//get Seller by id
	@GetMapping("/sellers/{sellerId}")
	public ResponseEntity<Seller> getSellerById(@PathVariable(value = "sellerId") Long sellerId) throws ResourceNotFoundException {
		Seller seller = sellerService.getSellerById(sellerId);
		return ResponseEntity.ok().body(seller);
	}
	
	//save Seller
	@PostMapping("sellers")
	public Seller createSeller(@RequestBody Seller seller) {
		Seller sellerResp = sellerService.addNewSeller(seller);
		return sellerResp;
	}
	
	//update Seller
	@PutMapping("sellers/{sellerId}")
	public ResponseEntity<Seller> updateSeller(@PathVariable(value = "sellerId") Long sellerId, @Valid @RequestBody Seller sellerDetails) throws ResourceNotFoundException{
//		Seller Seller = sellerRepository.findById(sellerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//		
//		Seller.setUsername(SellerDetails.getUsername());
//		Seller.setPassword(SellerDetails.getPassword());
//		Seller.setEmail(SellerDetails.getEmail());
//		Seller.setPhoneNumber(SellerDetails.getPhoneNumber());
//		Seller.setAddress(SellerDetails.getAddress());
		
		Seller seller = sellerService.updateSeller(sellerId, sellerDetails);
		
		return ResponseEntity.ok(seller);
	}
	
	//delete Seller
	@DeleteMapping("sellers/{sellerId}")
	public Map<String, Boolean> deleteSeller(@PathVariable(value = "sellerId") Long sellerId) throws ResourceNotFoundException{
//		Seller Seller = sellerRepository.findById(sellerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
//		
//		this.sellerRepository.delete(Seller);
//		
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("Deleted", Boolean.TRUE);
//		
		Map<String, Boolean> response = sellerService.deleteSeller(sellerId);
		return response;
	}
}