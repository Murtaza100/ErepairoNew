package com.stackroute.orderStatusService.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stackroute.orderStatusService.dto.OrderStatusRequest;
import com.stackroute.orderStatusService.dto.OrderStatusResponse;
import com.stackroute.orderStatusService.dto.PaymentResponse;
import com.stackroute.orderStatusService.entity.OrderStatus;
import com.stackroute.orderStatusService.entity.ServiceStatus;
import com.stackroute.orderStatusService.entity.TrackStatus;
import com.stackroute.orderStatusService.exception.InvalidOrderStatusRequestException;
import com.stackroute.orderStatusService.repository.OrderStatusRepository;
import com.stackroute.orderStatusService.util.FeignClientPaymentUtil;

@SpringBootTest
class OrderStatusServiceTest {

	@MockBean
	OrderStatusRepository orderStatusRepository;

	@MockBean
	FeignClientPaymentUtil feignClientPaymentUtil;

	@Autowired
	OrderStatusService orderStatusService;

	OrderStatus getOrderStatus() {
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setBookingId(1);
		orderStatus.setCalculatedCost(100);
		orderStatus.setCreatedOn(LocalDateTime.now());
		orderStatus.setCurrentStatus(ServiceStatus.OPEN);
		orderStatus.setCustomerEmail("sushanth@globallogic.com");
		orderStatus.setStatusId("101");
		orderStatus.setServiceCenterName("serviceCenter1");
		List<TrackStatus> trackStatus = new ArrayList<>();
		trackStatus.add(new TrackStatus("track description", LocalDateTime.now()));
		orderStatus.setTrackList(trackStatus);
		return orderStatus;
	}
	
	@Test
	void testAddTrackStatus() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(getOrderStatus());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		OrderStatusResponse orderStatusResponse = orderStatusService.addTrackStatus(orderStatusRequest);
		assertNotNull(orderStatusResponse);
		assertEquals("101", orderStatusResponse.getId());
	}

	@Test
	void testAddTrackStatusThrowsExceptionInvalidBookingId() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(any());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(0);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.addTrackStatus(orderStatusRequest);
		});
	}
	
	@Test
	void testAddTrackStatusThrowsExceptionEmptyCurrentStatus() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(any());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.addTrackStatus(orderStatusRequest);
		});
	}
	
	@Test
	void testAddTrackStatusThrowsExceptionEmptyEmail() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(any());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.addTrackStatus(orderStatusRequest);
		});
	}
	
	@Test
	void testAddTrackStatusThrowsExceptionEmptyServiceCenter() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(any());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setTrackStatus("track description");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.addTrackStatus(orderStatusRequest);
		});
	}
	
	@Test
	void testAddTrackStatusThrowsExceptionEmptyTrackStatus() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(any());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.addTrackStatus(orderStatusRequest);
		});
	}
	
	@Test
	void testAddTrackStatusThrowsExceptionBookingIdAlreadyPresent() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.of(getOrderStatus()));
		when(orderStatusRepository.save(any())).thenReturn(any());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.addTrackStatus(orderStatusRequest);
		});
	}
	
	@Test
	void testGetTrackList() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.of(getOrderStatus()));
		assertNotNull(orderStatusService.getTrackList(1));
	}
	
	@Test
	void testGetTrackListThrowsException() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.getTrackList(1);
		});
	}
	
	@Test
	void testGetTrackListByServiceCenter() {
		when(orderStatusRepository.findByServiceCenterName(any())).thenReturn(Arrays.asList(getOrderStatus()));
		assertNotNull(orderStatusService.getTrackListByServiceCenter("serviceCenter1").get(0).getCurrentStatus());
	}
	
	@Test
	void testGetTrackListByServiceCenterThrowsException() {
		when(orderStatusRepository.findByServiceCenterName(any())).thenReturn(new ArrayList<>());
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.getTrackListByServiceCenter("ServiceCenter");
		});
	}
	
	@Test
	void testGetTrackListByServiceCenterAndCurrentStatus() {
		when(orderStatusRepository.findByServiceCenterNameAndCurrentStatus(any(),any())).thenReturn(Arrays.asList(getOrderStatus()));
		assertNotNull(orderStatusService.getTrackListByServiceCenterAndCurrentStatus("serviceCenter1","OPEN").get(0).getCurrentStatus());
	}
	
	@Test
	void testGetTrackListByServiceCenterAndCurrentStatusThrowsException() {
		when(orderStatusRepository.findByServiceCenterNameAndCurrentStatus(any(), any())).thenReturn(new ArrayList<>());
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.getTrackListByServiceCenterAndCurrentStatus("serviceCenter1","OPEN");
		});
	}
	
	@Test
	void testInitiatePayment() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.of(getOrderStatus()));
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setRedirectUrl("any urls");
		when(feignClientPaymentUtil.createStripePayment(any())).thenReturn(ResponseEntity.of(Optional.of(paymentResponse)));
		assertEquals(HttpStatus.OK,orderStatusService.initiatePayment(1).getStatusCode());
	}
	
	@Test
	void testInitiatePaymentThrowsExceptionInvalidBookingId() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setRedirectUrl("any urls");
		when(feignClientPaymentUtil.createStripePayment(any())).thenReturn(ResponseEntity.of(Optional.of(paymentResponse)));
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.initiatePayment(1);
		});
	}
	
	@Test
	void testUpdateTrackStatus() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.of(getOrderStatus()));
		when(orderStatusRepository.save(any())).thenReturn(getOrderStatus());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		assertNotNull(orderStatusService.updateTrackStatus(orderStatusRequest, 1));
	}
	
	@Test
	void testUpdateTrackStatusThrowsException() {
		when(orderStatusRepository.findByBookingId(anyInt())).thenReturn(Optional.empty());
		when(orderStatusRepository.save(any())).thenReturn(getOrderStatus());
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		assertThrows(InvalidOrderStatusRequestException.class, () -> {
			orderStatusService.updateTrackStatus(orderStatusRequest, 1);
		});
	}

}
