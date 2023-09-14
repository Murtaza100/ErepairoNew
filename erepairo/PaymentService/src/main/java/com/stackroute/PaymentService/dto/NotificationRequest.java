package com.stackroute.PaymentService.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class NotificationRequest.
 *
 * @author sushanth
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest implements Serializable{

	private static final long serialVersionUID = -7223032606989932606L;
	private String email;
	private String mailSubject;
	private String mailBody;

}
