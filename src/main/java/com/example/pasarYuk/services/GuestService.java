package com.example.pasarYuk.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Guest;
import com.example.pasarYuk.repository.GuestRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.HomeAdminDTO;

@Service
public class GuestService {

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private GuestRepository guestRepository;
	
	public Guest findGuestById(long guestId) throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
		return guest;
	}
	
	public HomeAdminDTO getDetailWebHome() {
		HomeAdminDTO temp = new HomeAdminDTO();
		
		int totalSeller = sellerRepository.getTotalSeller();
		int totalStaff = staffRepository.getTotalStaff();
		int totalGuestSeller = guestRepository.getTotalGuestSeller();
		int totalGuestStaff = guestRepository.getTotalGuestStaff();
		
		temp.setTotalSeller(totalSeller);
		temp.setTotalStaff(totalStaff);
		temp.setTotalGuestSeller(totalGuestSeller);
		temp.setTotalGuestStaff(totalGuestStaff);
		
		return temp;
	}
	
	public Guest addNewGuest(Guest guest) {
		return guestRepository.save(guest);
	}
	public Guest updateGuest(Long guestId, Guest guestDetails) throws ResourceNotFoundException {
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
		
		guest.setGuestName(guestDetails.getGuestName());
		guest.setEmail(guestDetails.getEmail());
		guest.setAddress(guestDetails.getAddress());
		guest.setPhoneNumber(guestDetails.getPhoneNumber());
		guest.setStatus(guestDetails.getStatus());
		guest.setType(guestDetails.getType());
		guest.setComment(guestDetails.getComment());
		
		return guestRepository.save(guest);
	}
	public Map<String, Boolean> deleteGuest(Long guestId) throws ResourceNotFoundException{
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new ResourceNotFoundException("Guest not found for this id :: " + guestId));
		
		this.guestRepository.delete(guest);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
}
