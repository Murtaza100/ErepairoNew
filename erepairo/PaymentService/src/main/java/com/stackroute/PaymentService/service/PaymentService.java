package com.stackroute.PaymentService.service;

import com.stackroute.PaymentService.dto.PaymentRequest;
import com.stackroute.PaymentService.dto.PaymentResponse;

/**
 * The Interface PaymentService.
 *
 * @author sushanth
 */
public interface PaymentService {

	/**
	 * Creates the payment sesssion.
	 *
	 * @param stripeRequest the stripe request
	 * @return the stripe response
	 */
	PaymentResponse createPaymentSesssion(PaymentRequest stripeRequest);

}
