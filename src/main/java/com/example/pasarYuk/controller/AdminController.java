package com.example.pasarYuk.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Guest;
import com.example.pasarYuk.services.AdminService;

import temp.HomeAdminDTO;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@GetMapping("/guestHome")
	public HomeAdminDTO  getDetailWebHome() {
		return adminService.getDetailWebHome();
	}
	
	
	@GetMapping("/admin/list/{type}")
	public List<Guest> listGuestSeller(@PathVariable(value = "type") String type) {
		return adminService.getListGuest(type);
	}
	
	@GetMapping("/admin/detail/{guestId}")
	public Guest detailGuest(@PathVariable(value = "guestId") Long guestId) throws ResourceNotFoundException {
		return adminService.getDetailGuest(guestId);
	}
	
	@PostMapping("/admin/approve/{guestId}")
	public Map<String, Boolean> approveGuest(@PathVariable(value = "guestId") Long guestId) throws ResourceNotFoundException{
		Map<String, Boolean> response = adminService.approveGuest(guestId);
		return response;
	}
	
	@PostMapping("/admin/decline/{guestId}")
	public ResponseEntity<Guest> declineGuest(@PathVariable(value = "guestId") Long guestId, @RequestBody String comment) throws ResourceNotFoundException {
		Guest guest = adminService.declineGuest(guestId, comment);

		return ResponseEntity.ok(guest);
	}
}
