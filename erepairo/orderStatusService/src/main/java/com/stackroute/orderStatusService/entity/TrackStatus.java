package com.stackroute.orderStatusService.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class TrackStatus.
 *
 * @author sushanth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackStatus {

	private String description;
	private LocalDateTime updatedOn;

}
