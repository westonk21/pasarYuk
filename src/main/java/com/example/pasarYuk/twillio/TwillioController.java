package com.example.pasarYuk.twillio;

import java.io.Console;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pasarYuk.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class TwillioController {

	@Autowired
	private TwillioService twillioService;
	
	@Value("${app.twillio.fromPhoneNo}")
	private String from;
	
	@PostMapping("/sendSMS")
	public String sendSMS(@Valid @RequestBody smsRequest body) throws ResourceNotFoundException {
//		System.out.println(from);
		twillioService.sendSMS(body.getPhoneNumber(), from, body.getMessage());
		
		return "SMS Sended";
	}
}
