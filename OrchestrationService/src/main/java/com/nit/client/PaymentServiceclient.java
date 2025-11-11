package com.nit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nit.entity.Booking;
import com.nit.model.ResponseMessage;

@FeignClient(name = "PAYMENTSERVICE", url = "http://localhost:9092/payment")
public interface PaymentServiceclient {

	@PostMapping("/process")
	public ResponseMessage processPayment(@RequestBody Booking booking);

	@PostMapping("/refund")
	public ResponseMessage refundPayment(@RequestBody Booking booking);

}
