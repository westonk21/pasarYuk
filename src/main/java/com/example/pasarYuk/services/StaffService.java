package com.example.pasarYuk.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Order;
import com.example.pasarYuk.model.Product;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.OrderRepository;
import com.example.pasarYuk.repository.ProductRepository;
import com.example.pasarYuk.repository.SellerRepository;
import com.example.pasarYuk.repository.StaffRepository;



@Service
public class StaffService {

//	@Autowired
//	private OrderRepository orderRepository;
//	
//	@Autowired
//	private ProductRepository productRepository;
//	
//	@Autowired
//	private SellerRepository sellerRepository;
	
	private StaffRepository staffRepository;
	@Autowired
	public StaffService(StaffRepository staffRepository) {
		this.staffRepository = staffRepository;
	}
	
	public Staff findStaffById(long staffId) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		return staff;
	}
	
	//ACCEPT ORDER
	
	

	
	public Staff addNewStaff(Staff staff) {
		return staffRepository.save(staff);
	}
	
	public Staff updateStaff(Long staffId, Staff staffDetails) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
		staff.setAddress(staffDetails.getAddress());
		staff.setEmail(staffDetails.getEmail());
		staff.setPhoneNumber(staffDetails.getPhoneNumber());
		staff.setUrlStaffPhotoktp(staffDetails.getUrlStaffPhotoktp());
//		staff.setStaffNIK(staffDetails.getStaffNIK());
		staff.setActive(staffDetails.getActive());
		
		return this.staffRepository.save(staff);
	}
	
	public Map<String, Boolean> deleteStaff(Long staffId) throws ResourceNotFoundException{
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
		this.staffRepository.delete(staff);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}
}
