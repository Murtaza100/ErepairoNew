package com.stackroute.PaymentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class PaymentRequest.
 *
 * @author sushanth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentRequest {

	private int bookingId;
	private String serviceCenterName;
	private long amount;
	private String email;
}
