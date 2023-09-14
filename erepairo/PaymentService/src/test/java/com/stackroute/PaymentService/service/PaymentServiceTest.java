package com.stackroute.PaymentService.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.stackroute.PaymentService.dto.NotificationRequest;
import com.stackroute.PaymentService.dto.PaymentRequest;
import com.stackroute.PaymentService.entity.PaymentDetails;
import com.stackroute.PaymentService.exception.InvalidPaymentRequestException;
import com.stackroute.PaymentService.repository.PaymentRepository;

@SpringBootTest
@TestPropertySource(value = { "classpath:application-test.properties" })
public class PaymentServiceTest {

	@Value("${stripe.key.secret}")
	private String API_SECRET_KEY;

	@MockBean
	private RabbitTemplate template;

	@MockBean
	PaymentRepository paymentRepository;

	@Autowired
	PaymentService paymentService;

	@Test
	void testCreatePaymentSesssion(){
		when(paymentRepository.save(any())).thenReturn(new PaymentDetails());
		Mockito.doNothing().doThrow(new AmqpException("test exception")).when(template)
			.convertAndSend(anyString(), anyString(), Mockito.any(NotificationRequest.class));
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setBookingId(1);
		paymentRequest.setAmount(100l);
		paymentRequest.setEmail("sushanth.m@globallogic.com");
		paymentRequest.setServiceCenterName("serviceCenter1");
		assertNotNull(paymentService.createPaymentSesssion(paymentRequest));
	}

	@Test
	void testCreatePaymentSesssionThrowExceptionEmptyEmail(){
		when(paymentRepository.save(any())).thenReturn(new PaymentDetails());
		Mockito.doNothing().doThrow(new AmqpException("test exception")).when(template)
			.convertAndSend(anyString(), anyString(), Mockito.any(NotificationRequest.class));
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setBookingId(1);
		paymentRequest.setAmount(100l);
		paymentRequest.setServiceCenterName("serviceCenter1");
		assertThrows(InvalidPaymentRequestException.class, () -> {
			paymentService.createPaymentSesssion(paymentRequest);
		});
	}

	@Test
	void testCreatePaymentSesssionThrowInvalidAmount(){
		when(paymentRepository.save(any())).thenReturn(new PaymentDetails());
		Mockito.doNothing().doThrow(new AmqpException("test exception")).when(template)
			.convertAndSend(anyString(), anyString(), Mockito.any(NotificationRequest.class));
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setBookingId(1);
		paymentRequest.setEmail("sushanth.m@globallogic.com");
		paymentRequest.setAmount(0l);
		paymentRequest.setServiceCenterName("serviceCenter1");
		assertThrows(InvalidPaymentRequestException.class, () -> {
			paymentService.createPaymentSesssion(paymentRequest);
		});
	}
}
