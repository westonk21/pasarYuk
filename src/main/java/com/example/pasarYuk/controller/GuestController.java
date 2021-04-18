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
import com.example.pasarYuk.model.Guest;
import com.example.pasarYuk.repository.GuestRepository;
import com.example.pasarYuk.services.GuestService;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class GuestController {

	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private GuestService guestService;
	
	@GetMapping("/guest")
	public List<Guest> viewAllGuest(){
		return guestRepository.findAll();
	}
	
	@GetMapping("/guest/type/{type}")
	public List<Guest> viewAllGuestType(@PathVariable(value = "type") String type){
		return guestRepository.listGuest(type);
	}
	
	@GetMapping("/guest/{guestId}")
	public Guest viewGuest(@PathVariable(value = "guestId") Long guestId) throws ResourceNotFoundException {
		return guestService.findGuestById(guestId);
	}
	
	@PostMapping("/guest/new")
	public Guest addGuest(@RequestBody Guest guest) {
		Guest guestResp =guestService.addNewGuest(guest);
		return guestResp;
	}
	
	@PutMapping("/guest/update/{guestId}")
	public ResponseEntity<Guest> updateGuest(@PathVariable(value = "guestId") Long guestId, @RequestBody Guest guestDetails) throws ResourceNotFoundException{
		Guest guest = guestService.updateGuest(guestId, guestDetails);
		return ResponseEntity.ok(guest);
	}
	
	@DeleteMapping("guest/delete/{guestId}")
	public Map<String, Boolean> deleteGuest(@PathVariable(value = "guestId") Long guestId) throws ResourceNotFoundException{
		Map<String, Boolean> response = guestService.deleteGuest(guestId);
		return response;
	}
}
