package com.stackroute.PaymentService.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentResponse {

	/** The redirect url. */
	private String redirectUrl;
}
