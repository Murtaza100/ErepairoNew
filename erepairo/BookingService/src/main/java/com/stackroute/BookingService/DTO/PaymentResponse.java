package com.stackroute.BookingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class PaymentResponse.
 *
 * @author sushanth
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentResponse {

	/** The redirect url. */
	private String redirectUrl;
}
