package com.example.pasarYuk.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Buyer;
import com.example.pasarYuk.model.ListOTP;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.repository.ListOtpRepository;
import com.example.pasarYuk.repository.SellerRepository;

@Service
public class SellerService {
	
	@Autowired
	private ListOtpRepository listOtpRepository;
	
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
	
	public Seller loginBuyer(String email, String iptPassword) throws ResourceNotFoundException {
		Seller seller = sellerRepository.findByEmail(email.toLowerCase());
		
		if(seller!=null) {
			String pwDB = seller.getPassword();
			String salt = seller.getSalt();
			boolean passwordMatch = EncryptService.verifyUserPassword(iptPassword, pwDB, salt);
			if(passwordMatch == true) {
				return seller;
			}else {
				throw new ResourceNotFoundException("Invalid Email/Password");
			}
		}else {
			throw new ResourceNotFoundException("Email Not Registered");
		}
	}
	
	public String registerBuyer(Seller sellerDtl, String otp) throws ResourceNotFoundException {
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeCurrent = date_format.format(dateTemp);

		ListOTP temp = listOtpRepository.findByEmailAndType(sellerDtl.getEmail().toLowerCase(), "Seller");
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
		
		return "Kami akan segera mengirim email untuk peninjauan lokasi Pasar";

//		Seller seller = sellerRepository.findByEmail(sellerDtl.getEmail().toLowerCase());
//		if(seller == null) {
//			Seller newSeller = new Seller();
//			newSeller.setSellerName(sellerDtl.getSellerName());
//			newSeller.setEmail(sellerDtl.getEmail().toLowerCase());
//			newSeller.setPhoneNumber(sellerDtl.getPhoneNumber());
//			newSeller.setMarketId(0);
//			
//			String salt = EncryptService.getSalt(30);
//			String pw = EncryptService.generateSecurePassword(sellerDtl.getPassword(), salt);
//			newSeller.setPassword(pw);
//			newSeller.setSalt(salt);
//			sellerRepository.save(newSeller);
//			return newSeller;
//		}else {
//			throw new ResourceNotFoundException("Email Already Registered, Please go to Login Page");
//		}
	}
	
	public Seller addNewSeller(Seller seller) {
		return sellerRepository.save(seller);
	}
	
	public Seller updateSeller(Long sellerId, Seller sellerDetails) throws ResourceNotFoundException{
		Seller seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Seller not found for this id :: " + sellerId));
		
		
		seller.setMarketId(sellerDetails.getMarketId());
		seller.setSellerName(sellerDetails.getSellerName());
		seller.setPassword(sellerDetails.getPassword());
		seller.setPhoneNumber(sellerDetails.getPhoneNumber());
		seller.setEmail(sellerDetails.getEmail());
//		seller.setAddress(sellerDetails.getAddress());
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
