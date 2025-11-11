package com.nit.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepo extends ReactiveMongoRepository<CustomerReactive, String> {

}
