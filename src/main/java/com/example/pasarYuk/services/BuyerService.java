package com.example.pasarYuk.services;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.ListOTP;
import com.example.pasarYuk.repository.BuyerRepository;
import com.example.pasarYuk.repository.ListOtpRepository;

@Service
public class BuyerService {
	
	@Autowired
	private ListOtpRepository listOtpRepository;
	
	
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
	
	public Buyer loginBuyer(String email, String iptPassword) throws ResourceNotFoundException {
		Buyer buyer = buyerRepository.findByEmail(email.toLowerCase());
		
		if(buyer!=null) {
			String pwDB = buyer.getPassword();
			String salt = buyer.getSalt();
			boolean passwordMatch = EncryptService.verifyUserPassword(iptPassword, pwDB, salt);
			if(passwordMatch == true) {
				return buyer;
			}else {
				throw new ResourceNotFoundException("Invalid Email/Password");
			}
		}else {
			throw new ResourceNotFoundException("Email Not Registered");
		}
	}
	
	public Buyer registerBuyer(Buyer buyerDtl, String otp) throws ResourceNotFoundException {
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeCurrent = date_format.format(dateTemp);

		ListOTP temp = listOtpRepository.findByEmailAndType(buyerDtl.getEmail().toLowerCase(), "Buyer");
		if(temp!=null) {
			String timeDB = temp.getTimestamp();
			
			String dateCur = timeCurrent.substring(0, 7);
			String dateDB = timeDB.substring(0, 7);
			if(dateCur.equals(dateDB)) {
				int a = Integer.parseInt(timeCurrent.substring(8, 13));
				int b = Integer.parseInt(timeDB.substring(8, 13));
				int c= a-b;
				if (c > 0 && c <500) {
					if(!temp.getOtp().equals(otp)) {
						throw new ResourceNotFoundException("Invalid OTP1");
					}
				}else {
					throw new ResourceNotFoundException("Invalid OTP2");
				}
			}else {
				throw new ResourceNotFoundException("Invalid OTP3");
			}
			
		}else {
			throw new ResourceNotFoundException("Invalid OTP4");
		}
		
		Buyer buyer = buyerRepository.findByEmail(buyerDtl.getEmail().toLowerCase());
		if(buyer == null) {
			Buyer newBuyer = new Buyer();
			newBuyer.setBuyerName(buyerDtl.getBuyerName());
			newBuyer.setEmail(buyerDtl.getEmail().toLowerCase());
			newBuyer.setAddress(buyerDtl.getAddress());
			newBuyer.setPhoneNumber(buyerDtl.getPhoneNumber());
			newBuyer.setMarketId(0);
			
			String salt = EncryptService.getSalt(30);
			String pw = EncryptService.generateSecurePassword(buyerDtl.getPassword(), salt);
			newBuyer.setPassword(pw);
			newBuyer.setSalt(salt);
			buyerRepository.save(newBuyer);
			return newBuyer;
		}else {
			throw new ResourceNotFoundException("Email Already Registered, Please go to Login Page");
		}
	}
	
	
	
	public Buyer updateBuyer(Long buyerId, Buyer buyerDetails) throws ResourceNotFoundException{
		Buyer buyer = buyerRepository.findById(buyerId)
				.orElseThrow(() -> new ResourceNotFoundException("Buyer not found for this id :: " + buyerId));
		
//		buyer.setUsername(buyerDetails.getUsername());
		buyer.setBuyerName(buyerDetails.getBuyerName());
//		buyer.setPassword(buyerDetails.getPassword());
		buyer.setEmail(buyerDetails.getEmail().toLowerCase());
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
