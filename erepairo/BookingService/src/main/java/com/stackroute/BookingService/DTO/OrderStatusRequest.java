package com.stackroute.BookingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class OrderStatusRequest.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequest {

	private int bookingId;
	private String trackStatus;
	private ServiceStatus currentStatus;
	private long calculatedCost;
	private String serviceCenterName;
	private String customerEmail;
}
