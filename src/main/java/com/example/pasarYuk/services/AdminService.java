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
import com.example.pasarYuk.repository.OrderRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.HomeAdminDTO;

@Service
public class AdminService {

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	private GuestRepository guestRepository;
	@Autowired
	public AdminService(GuestRepository guestRepository) {
		this.guestRepository = guestRepository;
	}
	
	
	public HomeAdminDTO getDetailWebHome() {
		HomeAdminDTO temp = new HomeAdminDTO();
		
		int totalOgOrder=0;
		int totalSuOrder=0;
		int totalCaOrder=0;
		
		int totalSeller = sellerRepository.getTotalSeller();
		int totalStaff = staffRepository.getTotalStaff();
		int totalGuestSeller = guestRepository.getTotalGuestSeller();
		int totalGuestStaff = guestRepository.getTotalGuestStaff();
		
		List<Integer> ongoingOrder = orderRepository.getTotalOngoingOrder();
		List<Integer> succesOrder = orderRepository.getTotalSuccesOrder();
		List<Integer> cancelOrder = orderRepository.getTotalCancelOrder();
		for (Integer integer : ongoingOrder) {
			totalOgOrder += integer;
		}
		for (Integer integer : succesOrder) {
			totalSuOrder += integer;
		}
		for (Integer integer : cancelOrder) {
			totalCaOrder += integer;
		}
		
		temp.setTotalSeller(totalSeller);
		temp.setTotalStaff(totalStaff);
		temp.setTotalGuestSeller(totalGuestSeller);
		temp.setTotalGuestStaff(totalGuestStaff);
		temp.setTotalOngoingOrder(totalOgOrder);
		temp.setTotalSuccesOrder(totalSuOrder);
		temp.setTotalCancelOrder(totalCaOrder);
		
		return temp;
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
//			seller.setAddress(guest.getAddress());
			seller.setLapakName(type);
			seller.setEmail(guest.getEmail());
			seller.setPassword(guest.getPassword());
			seller.setSalt(guest.getSalt());
			seller.setOpenTime(guest.getOpenTime());
			seller.setCloseTime(guest.getCloseTime());
//			seller.setToken(guest.getToken());
			
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
			staff.setUrlStaffPhotoktp(guest.getPhotoKtp());
			staff.setPhotoUrl(guest.getPhotoPas());
			staff.setActive("0");
			staff.setWorking("0");
			staff.setWorkingPo(0);
			staff.setPassword(guest.getPassword());
			staff.setSalt(guest.getSalt());
			staff.setAvgStar(0);
			
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
