package com.nit.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerReactive {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private Long contactId;
}
