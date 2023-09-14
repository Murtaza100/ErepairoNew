package com.stackroute.orderStatusService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.orderStatusService.dto.OrderStatusRequest;
import com.stackroute.orderStatusService.dto.OrderStatusResponse;
import com.stackroute.orderStatusService.dto.PaymentResponse;
import com.stackroute.orderStatusService.service.OrderStatusService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class OrderStatusController.
 *
 * @author sushanth
 */
@RestController
@RequestMapping("/orderStatus")
@EnableSwagger2
public class OrderStatusController {

	/** The order status service. */
	@Autowired
	OrderStatusService orderStatusService;

	/**
	 * Adds the track status.
	 *
	 * @param orderStatusRequest the order status request
	 * @return the response entity
	 */
	@PostMapping("/status")
	public ResponseEntity<OrderStatusResponse> addTrackStatus(@RequestBody OrderStatusRequest orderStatusRequest) {
		return new ResponseEntity<>(orderStatusService.addTrackStatus(orderStatusRequest), HttpStatus.ACCEPTED);
	}

	/**
	 * Gets the track list by booking id.
	 *
	 * @param bookingId the booking id
	 * @return the track list by booking id
	 */
	@GetMapping("/customer/status/{bookingId}")
	public ResponseEntity<OrderStatusResponse> getTrackListByBookingId(@PathVariable int bookingId) {
		return new ResponseEntity<>(orderStatusService.getTrackList(bookingId), HttpStatus.OK);
	}

	/**
	 * Gets the track list by service center.
	 *
	 * @param serviceCenterName the service center name
	 * @return the track list by service center
	 */
	@GetMapping("provider/status/{serviceCenterName}")
	public ResponseEntity<List<OrderStatusResponse>> getTrackListByServiceCenter(
			@PathVariable String serviceCenterName) {
		return new ResponseEntity<>(orderStatusService.getTrackListByServiceCenter(serviceCenterName), HttpStatus.OK);
	}

	/**
	 * Gets the track list by service center and current status.
	 *
	 * @param serviceCenterName the service center name
	 * @param currentStatus     the current status
	 * @return the track list by service center and current status
	 */
	@GetMapping("provider/status/{serviceCenterName}/{currentStatus}")
	public ResponseEntity<List<OrderStatusResponse>> getTrackListByServiceCenterAndCurrentStatus(
			@PathVariable String serviceCenterName, @PathVariable String currentStatus) {
		return new ResponseEntity<>(
				orderStatusService.getTrackListByServiceCenterAndCurrentStatus(serviceCenterName, currentStatus),
				HttpStatus.OK);
	}

	/**
	 * Initiate payment.
	 *
	 * @param bookingId the booking id
	 * @return the response entity
	 */
	@PostMapping("provider/payment/{bookingId}")
	public ResponseEntity<PaymentResponse> initiatePayment(@PathVariable int bookingId) {
		return orderStatusService.initiatePayment(bookingId);
	}

	/**
	 * Update track status.
	 *
	 * @param orderStatusRequest the order status request
	 * @param bookingId          the booking id
	 * @return the response entity
	 */
	@PutMapping("/status/{bookingId}")
	public ResponseEntity<OrderStatusResponse> updateTrackStatus(@RequestBody OrderStatusRequest orderStatusRequest,
			@PathVariable int bookingId) {
		return new ResponseEntity<>(orderStatusService.updateTrackStatus(orderStatusRequest,bookingId), HttpStatus.ACCEPTED);
	}

}
