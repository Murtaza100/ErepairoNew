package com.stackroute.BookingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class NotificationDto.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

	private String email;
	private String mailSubject;
	private String mailBody;

}
