package com.stackroute.orderStatusService.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class OrderStatus.
 *
 * @author sushanth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_status")
public class OrderStatus {
	@Id
	private String statusId;
	private int bookingId;
	private List<TrackStatus> trackList;
	private ServiceStatus currentStatus;
	private long calculatedCost;
	private String serviceCenterName;
	private LocalDateTime createdOn;
	private String customerEmail;
}
