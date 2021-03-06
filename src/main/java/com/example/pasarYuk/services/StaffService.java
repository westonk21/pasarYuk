package com.example.pasarYuk.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.example.pasarYuk.repository.OrderRepository;
import com.example.pasarYuk.repository.StaffRepository;

import temp.HomeStaffDTO;
import temp.LoginRequest;



@Service
public class StaffService {

	@Autowired
	private OrderRepository orderRepository;
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
	
	public HomeStaffDTO getDetailHome(Long staffId) throws ResourceNotFoundException {
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyy");
		String timeStamp = date_format.format(dateTemp)+"000000";
//		System.out.println(timeStamp);
		
		HomeStaffDTO temp = new HomeStaffDTO();
		
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		temp.setStaffName(staff.getStaffName());
		temp.setPhoneNumber(staff.getPhoneNumber());
		temp.setPhotoUrl(staff.getPhotoUrl());
		temp.setActive(staff.getActive());
		
		List<Integer> listOngoing = orderRepository.getTotalOngoingOrderWithStaffId(staffId, timeStamp);
		int totalOngoing = 0;
		for (Integer integer : listOngoing) {
			totalOngoing += integer;
		}
		temp.setOngoingOrder(totalOngoing);		
		Integer listSuccess = orderRepository.getTotalSuccessgOrderWithStaffId(staffId, timeStamp);
		if(listSuccess==null)listSuccess=0;
		temp.setFinishOrder(listSuccess);
		Integer listCancel = orderRepository.getTotalCancelgOrderWithStaffId(staffId, timeStamp);
		if(listCancel==null)listCancel=0;
		temp.setCancelOrder(listCancel);
		
		return temp;
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
				staff.setToken(login.getToken());
				staffRepository.save(staff);
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
				guest.setAddress(staffDtl.getAddress());
				guest.setType("staff");
				guest.setStatus("NEW");
				guest.setPhotoKtp(staffDtl.getUrlStaffPhotoktp());
				guest.setPhotoPas(staffDtl.getPhotoUrl());
				
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

	public HomeStaffDTO updateActive(Long staffId) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
		if(staff.getActive().equals("1")) {
			staff.setActive("0");
		}else {
			staff.setActive("1");
		}
		staffRepository.save(staff);
		
		return getDetailHome(staffId);
	}
	
	public String reviewStaff(Long staffId, float rate) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new ResourceNotFoundException("Staff not found for this id :: " + staffId));
		
		if(staff.getAvgStar()!=0) {
			float dbStar = staff.getAvgStar();
			float newStar = (dbStar + rate) / 2;
			DecimalFormat df = new DecimalFormat("#.#");
			String newTemp = df.format(newStar);
			float newTemp2 = Float.valueOf(newTemp).floatValue();
			staff.setAvgStar(newTemp2);
		}else {
			staff.setAvgStar(rate);
		}
		
		staffRepository.save(staff);
		
		return "Oke";
	}
}
