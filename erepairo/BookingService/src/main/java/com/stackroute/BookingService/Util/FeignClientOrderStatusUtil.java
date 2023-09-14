package com.stackroute.BookingService.Util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.stackroute.BookingService.DTO.PaymentResponse;

/**
 * The Interface FeignClientOrderStatusUtil.
 *
 * @author sushanth
 */
@FeignClient(value = "orderStatusFeign", url = "http://localhost:8080/orderStatus")
public interface FeignClientOrderStatusUtil {

	/**
	 * Initiate payment.
	 *
	 * @param bookingId the booking id
	 * @return the response entity
	 */
	@PostMapping("/provider/payment/{bookingId}")
	ResponseEntity<PaymentResponse> initiatePayment(@PathVariable int bookingId);

}
