package com.example.pasarYuk.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.example.pasarYuk.model.Guest;
import com.example.pasarYuk.model.ListOTP;
import com.example.pasarYuk.model.Seller;
import com.example.pasarYuk.model.Staff;
import com.example.pasarYuk.repository.GuestRepository;
import com.example.pasarYuk.repository.ListOtpRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.LoginRequest;



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
	
	
	@Autowired
	private GuestRepository guestRepository;
	@Autowired
	private ListOtpRepository listOtpRepository;
	
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
	
	public Staff loginStaff(LoginRequest login) throws ResourceNotFoundException {
		String email = login.getEmail().toLowerCase();
		String iptPassword = login.getPassword();
		
		Staff staff = staffRepository.findByEmail(email.toLowerCase());
		
		if(staff!=null) {
			String pwDB = staff.getPassword();
			String salt = staff.getSalt();
			boolean passwordMatch = EncryptService.verifyUserPassword(iptPassword, pwDB, salt);
			if(passwordMatch == true) {
				return staff;
			}else {
				throw new ResourceNotFoundException("Invalid Email/Password");
			}
		}else {
			throw new ResourceNotFoundException("Email Not Registered");
		}
	}
	
	public String registerStaff(Staff staffDtl, String otp) throws ResourceNotFoundException {
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeCurrent = date_format.format(dateTemp);

		ListOTP temp = listOtpRepository.findByEmailAndType(staffDtl.getEmail().toLowerCase(), "Staff");
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
					throw new ResourceNotFoundException("OTP has been Expired");
				}
			}else {
				throw new ResourceNotFoundException("OTP out of Date");
			}
		}else {
			throw new ResourceNotFoundException("Invalid OTP4");
		}
		
		

		Staff staff = staffRepository.findByEmail(staffDtl.getEmail().toLowerCase());
		if(staff == null) {
			Guest guestTemp = guestRepository.findByEmail(staffDtl.getEmail().toLowerCase(), "staff");
			if(guestTemp==null) {
				Guest guest = new Guest();
				guest.setGuestName(staffDtl.getStaffName());
				guest.setPhoneNumber(staffDtl.getPhoneNumber());
				guest.setEmail(staffDtl.getEmail());
				guest.setType("staff");
				guest.setStatus("NEW");
				
				String salt = EncryptService.getSalt(30);
				String pw = EncryptService.generateSecurePassword(staffDtl.getPassword(), salt);
				guest.setPassword(pw);
				guest.setSalt(salt);
//				buyerRepository.save(newBuyer);
				guestRepository.save(guest);
			}else {
				throw new ResourceNotFoundException("Email Already Registered, Please go to Login Page");
			}
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
		}else {
			throw new ResourceNotFoundException("Email Already Registered, Please go to Login Page");
		}
		return "Kami akan segera mengirim email untuk Wawancara";
	}
	
	public Staff updateStaff(Long staffId, Staff staffDetails) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
//		staff.setStaffNIK(staffDetails.getStaffNIK());
		staff.setStaffName(staffDetails.getStaffName());
		staff.setPassword(staffDetails.getPassword());
		staff.setPhoneNumber(staffDetails.getPhoneNumber());
		staff.setEmail(staffDetails.getEmail());
		staff.setAddress(staffDetails.getAddress());
		staff.setUrlStaffPhotoktp(staffDetails.getUrlStaffPhotoktp());
		staff.setActive(staffDetails.getActive());
		staff.setMarketId(staffDetails.getMarketId());
		staff.setWorking(staffDetails.getWorking());
		
		return this.staffRepository.save(staff);
	}
	
	public Map<String, Boolean> deleteStaff(Long staffId) throws ResourceNotFoundException{
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
		this.staffRepository.delete(staff);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
		return response;
	}

	public Staff updateActive(Long staffId) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
		if(staff.getActive().equals("1")) {
			staff.setActive("0");
		}else {
			staff.setActive("1");
		}
		
		return this.staffRepository.save(staff);
	}
}
