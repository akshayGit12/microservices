package com.nit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nit.entity.Booking;
import com.nit.model.ResponseMessage;

@FeignClient(name = "NOTIFICATIONSERVICE", url = "http://localhost:9093/notify")
public interface NotificationServiceClient {

	@PostMapping("/email")
	public ResponseEntity<ResponseMessage> sendMail(@RequestBody Booking booking);

	@PostMapping("/sms")
	public ResponseEntity<ResponseMessage> sendSms(@RequestBody Booking booking);
}
