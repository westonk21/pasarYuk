package com.example.pasarYuk.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Guest;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.GuestRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;

@Service
public class AdminService {

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	private GuestRepository guestRepository;
	@Autowired
	public AdminService(GuestRepository guestRepository) {
		this.guestRepository = guestRepository;
	}
	
	public List<Guest> getListGuest(String type) {
		return guestRepository.listGuest(type);
	}
	
	public Guest getDetailGuest(Long guestId) throws ResourceNotFoundException {
		return guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
	}
	
	public Map<String, Boolean> approveGuest(Long guestId) throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
		String type = guest.getType();
		int flag = 0;
		
		//save ke type nya (seller/staff)
		if(type.equals("seller")) {
			Seller seller = new Seller();
			
			seller.setSellerName(guest.getGuestName());
			seller.setPhoneNumber(guest.getPhoneNumber());
			seller.setEmail(guest.getEmail());
			seller.setAddress(guest.getAddress());
			seller.setLapakName(type);
			seller.setEmail(guest.getEmail());
			
			sellerRepository.save(seller);
			if(ResponseEntity.ok(seller) != null) {
				flag = 1;
			}
		}else if(type.equals("staff")) {
			Staff staff = new Staff();
			
			staff.setStaffName(guest.getGuestName());
			staff.setPhoneNumber(guest.getPhoneNumber());
			staff.setEmail(guest.getEmail());
			staff.setAddress(guest.getAddress());
			staff.setActive("OFF");
			
			staffRepository.save(staff);
			if(ResponseEntity.ok(staff) != null) {
				flag = 1;
			}
		}
		//hapus dari guest
		if(flag == 0 ) {
			throw new ResourceNotFoundException("Error Saving Guest to Database");
		}
		this.guestRepository.delete(guest);
		Map<String, Boolean> response = new HashMap<>();
		return response;
	}
	
	public Guest declineGuest(Long guestId, String comment) throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
		
		guest.setStatus("DECLINE");
		guest.setComment(comment);
		
		return this.guestRepository.save(guest);
	}
}
