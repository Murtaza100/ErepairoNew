package com.stackroute.orderStatusService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.stackroute.orderStatusService.entity.ServiceStatus;
import com.stackroute.orderStatusService.entity.TrackStatus;
import com.stackroute.orderStatusService.service.OrderStatusService;

@SpringBootTest
class OrderStatusControllerTest {

	@MockBean
	OrderStatusService orderStatusService;

	@Autowired
	OrderStatusController orderStatusController;

	OrderStatusResponse getOrderStatus() {
		OrderStatusResponse orderStatus = new OrderStatusResponse();
		orderStatus.setBookingId(1);
		orderStatus.setCalculatedCost(100);
		orderStatus.setCreatedOn(LocalDateTime.now());
		orderStatus.setCurrentStatus(ServiceStatus.OPEN);
		orderStatus.setCustomerEmail("sushanth@globallogic.com");
		orderStatus.setId("101");
		orderStatus.setServiceCenterName("serviceCenter1");
		List<TrackStatus> trackStatus = new ArrayList<>();
		trackStatus.add(new TrackStatus("track description", LocalDateTime.now()));
		orderStatus.setTrackList(trackStatus);
		return orderStatus;
	}

	OrderStatusRequest createOrderRequest() {
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setBookingId(1);
		orderStatusRequest.setCalculatedCost(100);
		orderStatusRequest.setCurrentStatus(ServiceStatus.OPEN);
		orderStatusRequest.setCustomerEmail("sushanth@globallogic.com");
		orderStatusRequest.setServiceCenterName("serviceCenter1");
		orderStatusRequest.setTrackStatus("track description");
		return orderStatusRequest;
	}

	@Test
	void testAddTrackStatus() {
		when(orderStatusService.addTrackStatus(any())).thenReturn(getOrderStatus());
		assertEquals(HttpStatus.ACCEPTED, orderStatusController.addTrackStatus(createOrderRequest()).getStatusCode());
	}
	
	@Test
	void testGetTrackListByBookingId() {
		when(orderStatusService.getTrackList(anyInt())).thenReturn(getOrderStatus());
		assertEquals(HttpStatus.OK, orderStatusController.getTrackListByBookingId(1).getStatusCode());
	}
	
	@Test
	void testGetTrackListByServiceCenter() {
		when(orderStatusService.getTrackListByServiceCenter(anyString())).thenReturn(Arrays.asList(getOrderStatus()));
		assertEquals(HttpStatus.OK, orderStatusController.getTrackListByServiceCenter("serviceCenter").getStatusCode());
	}
	
	@Test
	void testGetTrackListByServiceCenterAndCurrentStatus() {
		when(orderStatusService.getTrackListByServiceCenterAndCurrentStatus(anyString(), anyString())).thenReturn(Arrays.asList(getOrderStatus()));
		assertEquals(HttpStatus.OK, orderStatusController.getTrackListByServiceCenterAndCurrentStatus("serviceCenter", "OPEN").getStatusCode());
	}
	
	@Test
	void testInitiatePayment() {
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setRedirectUrl("any urls");
		when(orderStatusService.initiatePayment(anyInt())).thenReturn(ResponseEntity.of(Optional.of(paymentResponse)));
		assertEquals(HttpStatus.OK, orderStatusController.initiatePayment(1).getStatusCode());
	}
	
	@Test
	void testUpdateTrackStatus() {
		when(orderStatusService.updateTrackStatus(any(), anyInt())).thenReturn(getOrderStatus());
		assertEquals(HttpStatus.ACCEPTED, orderStatusController.updateTrackStatus(createOrderRequest(),1).getStatusCode());
	}

}
