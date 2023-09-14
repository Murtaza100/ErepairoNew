package com.stackroute.PaymentService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.PaymentService.dto.PaymentRequest;
import com.stackroute.PaymentService.dto.PaymentResponse;
import com.stackroute.PaymentService.service.PaymentService;

/**
 * The Class PaymentController.
 *
 * @author sushanth
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {

	Logger log = LoggerFactory.getLogger(PaymentController.class);

	/** The payment service. */
	@Autowired
	PaymentService paymentService;

	/**
	 * Creates the stripe payment.
	 *
	 * @param stripeRequest the stripe request
	 * @return the response entity
	 */
	@PostMapping("/create-charge")
	public ResponseEntity<PaymentResponse> createStripePayment(@RequestBody PaymentRequest stripeRequest) {
		log.info("Starting to execute PaymentController.createStripePayment");
		PaymentResponse stripeResponse = paymentService.createPaymentSesssion(stripeRequest);

		return new ResponseEntity<PaymentResponse>(stripeResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * Display success message.
	 *
	 * @return the response entity
	 */
	@GetMapping("/success")
	public ResponseEntity<String> displaySuccessMessage() {
		log.info("Starting to execute PaymentController.displaySuccessMessage");
		return new ResponseEntity<String>("Payment was successful. Thank you", HttpStatus.OK);
	}

	/**
	 * Display cancel message.
	 *
	 * @return the response entity
	 */
	@GetMapping("/cancel")
	public ResponseEntity<String> displayCancelMessage() {
		log.info("Starting to execute PaymentController.displayCancelMessage");
		return new ResponseEntity<String>("Payment was cancelled. Please try again", HttpStatus.NOT_FOUND);
	}

}
