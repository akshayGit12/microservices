package com.nit.controller;

import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nit.client.BookingServiceClient;
import com.nit.client.NotificationServiceClient;
import com.nit.client.PaymentServiceclient;
import com.nit.entity.Booking;
import com.nit.model.ResponseMessage;
import com.nit.utility.Constants;

@RestController
@RequestMapping("/orchestration")
public class OrchestrationController {

//	@Autowired
///	private RestTemplate restTemplate;

	@Autowired
	BookingServiceClient bsclient;

	@Autowired
	PaymentServiceclient paymentServiceclient;

	@Autowired
	NotificationServiceClient notificationServiceClient;

	@PostMapping("/start")
	public ResponseEntity<ResponseMessage> sagaStart(@RequestBody Booking booking) {

		boolean bookingDone = false;
		boolean paymentDone = false;

		try {
			// booking service
//			String bookingUrl = "http://localhost:9091/booking/create";
//			restTemplate.postForObject(bookingUrl, booking, String.class);

			// feign
			ResponseMessage bookResponse = bsclient.createBook(booking);
			bookingDone = true;

			// paymentService
//			String paymentUrl = "http://localhost:9092/payment/process";
//			String payemntResponse = restTemplate.postForObject(paymentUrl, booking, String.class);

			// PaymentService Feign Client
			ResponseMessage processPayment = paymentServiceclient.processPayment(booking);

			if (processPayment != null && processPayment.getStatus().contains("FAILED")) {

//  			String cancelUrl = "http://localhost:9091/booking/cancel";
//				restTemplate.postForObject(cancelUrl, booking, String.class);
				bsclient.bookingsCancel(booking);

				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED,
						"Payment failed, booking cancelled automatically"));
			}
			paymentDone = true;

			// noticationService
//			String noticationUrl = "http://localhost:9093/notify/sms";
//			restTemplate.postForObject(noticationUrl, booking, String.class);
			notificationServiceClient.sendSms(booking);

			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,
					"Saga completed successfully — Booking, Payment, and Notification done!"));

		} catch (Exception e) {

			e.printStackTrace();

			try {
				if (paymentDone) {
//					restTemplate.postForObject("http://localhost:9092/payment/refund", booking, String.class);
					paymentServiceclient.refundPayment(booking);
					System.out.println(" Payment refund triggered");
				}

				if (bookingDone) {
//					restTemplate.postForObject("http://localhost:9091/booking/cancel", booking, String.class);
					bsclient.bookingsCancel(booking);
					System.out.println(" Booking cancelled due to failure");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED,
					"Saga failed! Compensation triggered for booking ID: " + booking.getId() + ". Error details: "
							+ e.getMessage()));

		}
	}

}