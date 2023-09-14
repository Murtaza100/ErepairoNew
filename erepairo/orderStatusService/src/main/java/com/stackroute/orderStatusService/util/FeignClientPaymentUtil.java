package com.stackroute.orderStatusService.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stackroute.orderStatusService.dto.PaymentRequest;
import com.stackroute.orderStatusService.dto.PaymentResponse;

/**
 * The Interface FeignClientPaymentUtil.
 *
 * @author sushanth
 */
@FeignClient(value = "paymentFeign", url = "http://localhost:8080/payments")
public interface FeignClientPaymentUtil {

	/**
	 * Creates the stripe payment.
	 *
	 * @param paymentRequest the payment request
	 * @return the response entity
	 */
	@PostMapping("/create-charge")
	ResponseEntity<PaymentResponse> createStripePayment(@RequestBody PaymentRequest paymentRequest);

}
