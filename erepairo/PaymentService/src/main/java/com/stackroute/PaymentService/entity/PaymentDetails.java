package com.stackroute.PaymentService.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class PaymentDetails.
 *
 * @author sushanth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "payment_details")
public class PaymentDetails {
	@Id
	private String id;
	private int bookingId;
	private String serviceCenterName;
	private long amount;
	private String email;
	private LocalDateTime paymentInitiatedDate;
}
