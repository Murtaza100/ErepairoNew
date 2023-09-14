package com.stackroute.PaymentService.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stackroute.PaymentService.dto.PaymentResponse;
import com.stackroute.PaymentService.service.PaymentService;

@SpringBootTest
public class PaymentControllerTest {

	@MockBean
	PaymentService paymentService;
	
	@Autowired
	PaymentController paymentController;
	
	@Test
	void testCreatePaymentSesssion() {
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setRedirectUrl("any urls");
		when(paymentService.createPaymentSesssion(any())).thenReturn(paymentResponse);
		assertNotNull(paymentController.createStripePayment(any()));
	}
	
	@Test
	void testSuccessPage() {
		assertNotNull(paymentController.displaySuccessMessage());
	}
	
	@Test
	void testCancelPage() {
		assertNotNull(paymentController.displayCancelMessage());
	}
	
}
