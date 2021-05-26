package com.example.pasarYuk.services;

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
	
	public Buyer loginBuyer(String email, String iptPassword) {
		Buyer buyer = buyerRepository.findByEmail(email);
		
		if(buyer!=null) {
			String pwDB = buyer.getPassword();
			String salt = buyer.getSalt();
			boolean passwordMatch = EncryptService.verifyUserPassword(iptPassword, pwDB, salt);
			if(passwordMatch == true) {
				return buyer;
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
	
	public Buyer registerBuyer(Buyer buyerDtl) {
		
		Buyer buyer = buyerRepository.findByEmail(buyerDtl.getEmail());
		if(buyer == null) {
			Buyer newBuyer = new Buyer();
			newBuyer.setBuyerName(buyerDtl.getBuyerName());
			newBuyer.setEmail(buyerDtl.getEmail());
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
			return null;
		}
	}
	
	public String sendOTP(String email) {
		//generate OTP
		String numbers = "0123456789";
        Random rndm_method = new Random();
        char[] otp = new char[4];
        for (int i = 0; i < 4; i++){
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
		int otpFinal = Integer.parseInt(new String(otp));
		//sendOTP to email
		
		ListOTP temp = listOtpRepository.findByEmail();
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeStamp = date_format.format(dateTemp);
		if(temp != null) {
			temp.setTimestamp(timeStamp);
			listOtpRepository.save(temp);
//			return temp;
		}else {
			ListOTP newTemp = new ListOTP();
			newTemp.setEmail(email);
			newTemp.setOtp(otpFinal);
			newTemp.setTimestamp(timeStamp);
		}
		
		return null;
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
