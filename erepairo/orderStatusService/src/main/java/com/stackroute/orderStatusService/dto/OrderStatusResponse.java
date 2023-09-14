package com.stackroute.orderStatusService.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.stackroute.orderStatusService.entity.ServiceStatus;
import com.stackroute.orderStatusService.entity.TrackStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class OrderStatusResponse.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {
	private String id;
	private int bookingId;
	private List<TrackStatus> trackList;
	private ServiceStatus currentStatus;
	private long calculatedCost;
	private String serviceCenterName;
	private LocalDateTime createdOn;
	private String customerEmail;
}
