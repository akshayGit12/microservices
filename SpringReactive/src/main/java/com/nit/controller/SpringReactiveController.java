package com.nit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nit.entity.CustomerReactive;
import com.nit.entity.CustomerRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SpringReactiveController {

	@Autowired
	CustomerRepo customerRepo;

	@PostMapping("/savecustomer")
	public Mono<CustomerReactive> createCustomer(@RequestBody CustomerReactive customerReactive) {

		return customerRepo.save(customerReactive);
	}

	@GetMapping("/getAllCustomer")
	public Flux<CustomerReactive> getAllCustomer() {
		return customerRepo.findAll();
	}

}
