package com.stackroute.orderStatusService.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.stackroute.orderStatusService.dto.OrderStatusRequest;
import com.stackroute.orderStatusService.dto.OrderStatusResponse;
import com.stackroute.orderStatusService.dto.PaymentResponse;

/**
 * The Interface OrderStatusService.
 *
 * @author sushanth
 */
public interface OrderStatusService {

	/**
	 * Adds the track status.
	 *
	 * @param orderStatusRequest the order status request
	 * @return the order status response
	 */
	OrderStatusResponse addTrackStatus(OrderStatusRequest orderStatusRequest);

	/**
	 * Gets the track list.
	 *
	 * @param bookingId the booking id
	 * @return the track list
	 */
	OrderStatusResponse getTrackList(int bookingId);

	/**
	 * Gets the track list by service center.
	 *
	 * @param serviceCenterName the service center name
	 * @return the track list by service center
	 */
	List<OrderStatusResponse> getTrackListByServiceCenter(String serviceCenterName);

	/**
	 * Gets the track list by service center and current status.
	 *
	 * @param serviceCenterName the service center name
	 * @param currentStatus     the current status
	 * @return the track list by service center and current status
	 */
	List<OrderStatusResponse> getTrackListByServiceCenterAndCurrentStatus(String serviceCenterName,
			String currentStatus);

	/**
	 * Initiate payment.
	 *
	 * @param bookingId the booking id
	 * @return the response entity
	 */
	ResponseEntity<PaymentResponse> initiatePayment(int bookingId);

	/**
	 * Update track status.
	 *
	 * @param orderStatusRequest the order status request
	 * @param bookingId          the booking id
	 * @return the order status response
	 */
	OrderStatusResponse updateTrackStatus(OrderStatusRequest orderStatusRequest, int bookingId);

	/**
	 * Checks if is booking id present.
	 *
	 * @param bookingId the booking id
	 * @return true, if is booking id present
	 */
	boolean isBookingIdPresent(int bookingId);
}
