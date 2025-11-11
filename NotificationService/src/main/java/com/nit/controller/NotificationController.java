package com.nit.controller;

import java.net.HttpURLConnection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nit.entity.Booking;
import com.nit.model.ResponseMessage;
import com.nit.utility.Constants;

@RestController
@RequestMapping("/notify")
public class NotificationController {

	@PostMapping("/send")
	public ResponseEntity<ResponseMessage> sendMail(@RequestBody Booking booking) {
		try {
			// Simulate sending mail
			System.out.println(" Mail sent successfully to: " + booking.getEmail());

			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
					"Mail sent successfully to " + booking.getEmail()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED,
					"Error while sending mail: " + e.getMessage()));
		}
	}

	@PostMapping("/sms")
	public ResponseEntity<ResponseMessage> sendSms(@RequestBody Booking booking) {
		try {
			// Simulate sending Sms
			System.out.println(" Sms sent message to: " + booking.getEmail());
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
					"Sms sent successfully to " + booking.getEmail()));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED,
					"Sms while sending fail: " + e.getMessage()));
		}
	}

}
