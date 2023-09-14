package com.stackroute.orderStatusService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.orderStatusService.entity.OrderStatus;

/**
 * The Interface OrderStatusRepository.
 *
 * @author sushanth
 */
@Repository
public interface OrderStatusRepository extends MongoRepository<OrderStatus, String> {

	/**
	 * Find by booking id.
	 *
	 * @param bookingId the booking id
	 * @return the optional
	 */
	Optional<OrderStatus> findByBookingId(int bookingId);

	/**
	 * Find by service center name.
	 *
	 * @param serviceCenterName the service center name
	 * @return the list
	 */
	List<OrderStatus> findByServiceCenterName(String serviceCenterName);

	/**
	 * Find by service center name and current status.
	 *
	 * @param serviceCenterName the service center name
	 * @param currentStatus     the current status
	 * @return the list
	 */
	List<OrderStatus> findByServiceCenterNameAndCurrentStatus(String serviceCenterName, String currentStatus);
}
