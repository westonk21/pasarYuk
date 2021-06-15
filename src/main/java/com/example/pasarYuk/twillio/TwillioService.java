package com.example.pasarYuk.twillio;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.pasarYuk.exception.ResourceNotFoundException;
import com.twilio.Twilio;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class TwillioService {

	@Value("${app.twillio.accountSID}")
	private String accSID;
	
	@Value("${app.twillio.authToken}")
	private String authToken;
	
	public void sendSMS(String to, String from, String body) throws ResourceNotFoundException {
		try {
			Twilio.init(accSID, authToken);
			Message msg = Message.creator(new PhoneNumber(to), new PhoneNumber(from), body)
					.create();
//			creator.create();
			System.out.println(msg.getSid());
		} catch (Exception e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Error sending Message");
		}
	}
}
