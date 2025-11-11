package com.nit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nit.entity.Booking;
import com.nit.model.ResponseMessage;

@FeignClient(name = "BOOKINGSERVICE", url = "http://localhost:9091/booking")
public interface BookingServiceClient {

	@PostMapping("/create")
	public ResponseMessage createBook(@RequestBody Booking booking);

	@PostMapping("/cancel")
	public ResponseMessage bookingsCancel(@RequestBody Booking booking);

}
