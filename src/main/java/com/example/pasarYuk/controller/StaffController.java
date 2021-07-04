package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

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
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.StaffRepository;
import com.example.pasarYuk.services.EmailService;
import com.example.pasarYuk.services.StaffService;

import temp.HomeStaffDTO;
import temp.LoginRequest;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class StaffController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private StaffService staffService;
	
	//@CrossOrigin
	@GetMapping("/staff")
	public List<Staff> viewAllStaff(){
		return staffRepository.findAll();
	}
	
	//home staff
	@GetMapping("/staff-home/{staffId}")
	public HomeStaffDTO viewHome(@PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException {
		return staffService.getDetailHome(staffId);
	}
	
	// view staff by id
	@GetMapping("/staff/{staffId}")
	public Staff viewStaff(@PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException {
		return staffService.findStaffById(staffId);
	}
	
	//new staff
	@PostMapping("/staff/new")
	public Staff addStaff(@RequestBody Staff staff) {
		Staff staffResp = staffService.addNewStaff(staff);
		return staffResp;
	}
	
	
	//login
	@PostMapping("/staffLogin")
	public Staff loginBuyer(@Valid @RequestBody LoginRequest loginReq) throws ResourceNotFoundException {
		Staff staffResp = staffService.loginStaff(loginReq);
		return staffResp;
	}
	//register
	@PostMapping("/staffRegister/{otp}")
	public String registerBuyer(@Valid @RequestBody Staff staff, @PathVariable(value = "otp") String otp) throws ResourceNotFoundException {
		String staffResp = staffService.registerStaff(staff, otp);
		return staffResp;
	}
	
	//send otp
	@PostMapping("/staffOTP/{email}")
	public String sendOTP(@PathVariable(value = "email") String email) {
		String emailLC = email.toLowerCase();
		String staffResp = emailService.sendOTP(emailLC, "Staff");
		return staffResp;
	}
	
	//review Staff
	@PostMapping("/staffReview/{staffId}/{rate}")
	public String reviewStaff(@PathVariable(value = "staffId") Long staffId, @PathVariable(value = "rate") float rate) throws ResourceNotFoundException {
		String temp = staffService.reviewStaff(staffId, rate);
		return temp;
	}
	
	//update active or not 
	@PutMapping("/staff/active/update/{staffId}")
	public ResponseEntity<HomeStaffDTO> updateActive(@PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException {
		HomeStaffDTO staff = staffService.updateActive(staffId);
		return ResponseEntity.ok(staff);
	}
	
	//update staff
	@PutMapping("/staff/update/{staffId}")
	public ResponseEntity<Staff> updateStaff(@PathVariable(value = "staffId") Long staffId, @RequestBody Staff staffDetails) throws ResourceNotFoundException{
		Staff staff = staffService.updateStaff(staffId, staffDetails);
		return ResponseEntity.ok(staff);
	}
	
	//delete staff
	@DeleteMapping("/staff/del/{staffId}")
	public Map<String, Boolean> deleteStaff(@PathVariable(value = "staffId") Long staffId) throws ResourceNotFoundException{
		Map<String, Boolean> response = staffService.deleteStaff(staffId);
		return response;
	}
}
