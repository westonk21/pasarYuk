package com.example.pasarYuk.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.model.ListOTP;
import com.example.pasarYuk.repository.ListOtpRepository;

@Service
public class EmailService {

	@Autowired
	private ListOtpRepository listOtpRepository;

	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendEmail(String email, String otp) {
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setFrom("pasaryukk@gmail.com");
		mail.setTo(email);
		mail.setSubject("Request Kode OTP");
		mail.setText("Kode OTP kamu adalah " + otp + ".    Kode hanya berlaku selama 5 menit.    Happy Shopping!!!");
		
//		MimeMessagePreparator mailMessage = mimeMessage -> {
//
//            MimeMessageHelper message = new MimeMessageHelper(
//                    mimeMessage, true, ENCODING);
//            try {
//                message.setFrom(senderEmail, senderName);
//                for (String addr : recipientEmails) {
//                    message.addTo(addr);
//                }
//                message.setReplyTo(senderEmail);
//                message.setSubject(subject);
//                message.setText(fallbackTextContent, htmlContent);
//            } catch (Exception e) {
//                throw new MailDeliveryServiceException(recpStr, e);
//            }
//        };
//        MimeMessagePreparator tes = new mim
		
		javaMailSender.send(mail);
//		return "Success";
	}
	
	public String sendOTP(String email, String Type) {
		//generate OTP
		String numbers = "0123456789";
        Random rndm_method = new Random();
        char[] otp = new char[4];
        for (int i = 0; i < 4; i++){
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
		String otpFinal = new String(otp);
		//sendOTP to email
		sendEmail(email, otpFinal);
		
		
		Date dateTemp = new Date();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
		SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyyHHmmss");
		String timeStamp = date_format.format(dateTemp);
		
		ListOTP temp = listOtpRepository.findByEmailAndType(email, Type);
		if(temp != null) {
			temp.setOtp(otpFinal);
			temp.setTimestamp(timeStamp);
			listOtpRepository.save(temp);
			
		}else {
			ListOTP newTemp = new ListOTP();
			newTemp.setEmail(email);
			newTemp.setOtp(otpFinal);
			newTemp.setTimestamp(timeStamp);
			newTemp.setType(Type);
			listOtpRepository.save(newTemp);
		}
		
		return "Success Send OTP";
	}
}
