package com.stackroute.PaymentService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.PaymentService.entity.PaymentDetails;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentDetails, String>{

}
