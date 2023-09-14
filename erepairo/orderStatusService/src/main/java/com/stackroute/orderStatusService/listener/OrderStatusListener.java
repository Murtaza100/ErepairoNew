package com.stackroute.orderStatusService.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.orderStatusService.dto.OrderStatusRequest;
import com.stackroute.orderStatusService.service.OrderStatusService;
import com.stackroute.orderStatusService.util.AppConstants;

/**
 * The listener interface for receiving orderStatus events. The class that is
 * interested in processing a orderStatus event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addOrderStatusListener<code> method. When the orderStatus
 * event occurs, that object's appropriate method is invoked.
 *
 * @author sushanth
 */
@Component
public class OrderStatusListener {

	/** The log. */
	Logger log = LoggerFactory.getLogger(OrderStatusListener.class);

	/** The order status service. */
	@Autowired
	OrderStatusService orderStatusService;

	/**
	 * Listen order status.
	 *
	 * @param notificationDto the notification dto
	 */
	@RabbitListener(queues = AppConstants.STATUS_QUEUE)
	public void listenOrderStatus(OrderStatusRequest orderStatusRequest) {
		if (orderStatusService.isBookingIdPresent(orderStatusRequest.getBookingId())) {
			orderStatusService.updateTrackStatus(orderStatusRequest, orderStatusRequest.getBookingId());
		} else {
			orderStatusService.addTrackStatus(orderStatusRequest);
		}
	}
}
