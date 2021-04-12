package com.example.pasarYuk.controller;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.example.pasarYuk.model.Buyer;
//import com.example.pasarYuk.repository.BuyerRepository;
import com.example.pasarYuk.services.BuyerService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")

public class BuyerController {

	@Autowired
	private BuyerService buyerService;
	//private BuyerRepository buyerRepository;
	
	//get buyer
	@CrossOrigin
	@GetMapping("buyers")
	public List<Buyer> getAllBuyer(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("buyerId", 1);
		
		//return this.buyerRepository.findAll();
		//List<Buyer> buyer = (List<Buyer>) buyerRepository.testQuery("weto@gmail.com");
		//buyer.forEach(System.out::println);
		//return this.buyerRepository.findAll();
		return buyerService.listBuyer();
	}
	@GetMapping("buyers2")
	public List<Buyer> getAllBuyer2(HttpSession session){
		Integer id = (Integer)session.getAttribute("buyerId");
		System.out.println(id);
		//if want to clear the HttpSession
		//session.invalidate();
		
		//return this.buyerRepository.findAll();
		//List<Buyer> buyer = (List<Buyer>) buyerRepository.testQuery("weto@gmail.com");
		//buyer.forEach(System.out::println);
		//return this.buyerRepository.findAll();
		return buyerService.listBuyer();
	}
	
	//get buyer by id
	@GetMapping("/buyers/{buyerId}")
	public ResponseEntity<Buyer> getBuyerById(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException {
		Buyer buyer = buyerService.getBuyerById(buyerId);
		return ResponseEntity.ok().body(buyer);
	}
	
	//save buyer
	@PostMapping("buyers")
	public Buyer createBuyer(@RequestBody Buyer buyer) {
		Buyer buyerResp = buyerService.addNewBuyer(buyer);
		return buyerResp;
	}
	
	//update buyer
	@PutMapping("buyers/{buyerId}")
	public ResponseEntity<Buyer> updateBuyer(@PathVariable(value = "buyerId") Long buyerId, @Valid @RequestBody Buyer buyerDetails) throws ResourceNotFoundException{
//		Buyer buyer = buyerRepository.findById(buyerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
//		
//		buyer.setUsername(buyerDetails.getUsername());
//		buyer.setPassword(buyerDetails.getPassword());
//		buyer.setEmail(buyerDetails.getEmail());
//		buyer.setPhoneNumber(buyerDetails.getPhoneNumber());
//		buyer.setAddress(buyerDetails.getAddress());
		
		Buyer buyer = buyerService.updateBuyer(buyerId, buyerDetails);
		
		return ResponseEntity.ok(buyer);
	}
	
	//delete buyer
	@DeleteMapping("buyers/{buyerId}")
	public Map<String, Boolean> deleteBuyer(@PathVariable(value = "buyerId") Long buyerId) throws ResourceNotFoundException{
//		Buyer buyer = buyerRepository.findById(buyerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
//		
//		this.buyerRepository.delete(buyer);
//		
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("Deleted", Boolean.TRUE);
//		
		Map<String, Boolean> response = buyerService.deleteBuyer(buyerId);
		return response;
	}
}
