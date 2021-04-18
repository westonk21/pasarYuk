package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

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
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.StaffRepository;
import com.example.pasarYuk.services.StaffService;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class StaffController {

	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private StaffService staffService;
	
	//@CrossOrigin
	@GetMapping("/staff")
	public List<Staff> viewAllStaff(){
		return staffRepository.findAll();
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
