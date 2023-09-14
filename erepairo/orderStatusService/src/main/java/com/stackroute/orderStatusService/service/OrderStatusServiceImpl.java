package com.stackroute.orderStatusService.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stackroute.orderStatusService.dto.OrderStatusRequest;
import com.stackroute.orderStatusService.dto.OrderStatusResponse;
import com.stackroute.orderStatusService.dto.PaymentRequest;
import com.stackroute.orderStatusService.dto.PaymentResponse;
import com.stackroute.orderStatusService.entity.OrderStatus;
import com.stackroute.orderStatusService.entity.TrackStatus;
import com.stackroute.orderStatusService.exception.InvalidOrderStatusRequestException;
import com.stackroute.orderStatusService.repository.OrderStatusRepository;
import com.stackroute.orderStatusService.util.AppConstants;
import com.stackroute.orderStatusService.util.FeignClientPaymentUtil;

/**
 * The Class OrderStatusServiceImpl.
 *
 * @author sushanth
 */
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

	Logger log = LoggerFactory.getLogger(OrderStatusServiceImpl.class);

	@Autowired
	OrderStatusRepository orderStatusRepository;

	@Autowired
	FeignClientPaymentUtil feignClientPaymentUtil;

	/**
	 * Adds the track status.
	 *
	 * @param orderStatusRequest the order status request
	 * @return the order status response
	 */
	@Override
	public OrderStatusResponse addTrackStatus(OrderStatusRequest orderStatusRequest) {
		log.info("Started to execute OrderStatusServiceImpl.addTrackStatus");
		Optional<OrderStatus> optionalStatus = orderStatusRepository.findByBookingId(orderStatusRequest.getBookingId());

		if (optionalStatus.isPresent()) {
			log.error(String.format(AppConstants.BOOKING_ID_ALREADY_PRESENT, orderStatusRequest.getBookingId()));
			throw new InvalidOrderStatusRequestException(
					AppConstants.BOOKING_ID_ALREADY_PRESENT + orderStatusRequest.getBookingId());
		} else {
			log.info("Booking Id not present in database. Hence inserting the trackList");
			validateRequest(orderStatusRequest);
			OrderStatus orderStatus = new OrderStatus();
			orderStatus.setBookingId(orderStatusRequest.getBookingId());
			if(orderStatusRequest.getCalculatedCost()>0) {
				orderStatus.setCalculatedCost(orderStatusRequest.getCalculatedCost());
			}
			orderStatus.setCurrentStatus(orderStatusRequest.getCurrentStatus());
			orderStatus.setServiceCenterName(orderStatusRequest.getServiceCenterName());
			orderStatus.setCreatedOn(LocalDateTime.now());
			orderStatus.setCustomerEmail(orderStatusRequest.getCustomerEmail());
			List<TrackStatus> trackList = new ArrayList<>();
			TrackStatus trackStatus = new TrackStatus();
			trackStatus.setDescription(orderStatusRequest.getTrackStatus());
			trackStatus.setUpdatedOn(LocalDateTime.now());
			trackList.add(trackStatus);
			orderStatus.setTrackList(trackList);
			return convertEntityToDto(orderStatusRepository.save(orderStatus));
		}
	}

	/**
	 * Convert entity to dto.
	 *
	 * @param orderStatus the order status
	 * @return the order status response
	 */
	private OrderStatusResponse convertEntityToDto(OrderStatus orderStatus) {
		return new ModelMapper().map(orderStatus, OrderStatusResponse.class);
	}

	/**
	 * Gets the track list.
	 *
	 * @param bookingId the booking id
	 * @return the track list
	 */
	@Override
	public OrderStatusResponse getTrackList(int bookingId) {
		log.info("Started to execute OrderStatusServiceImpl.getTrackList");
		Optional<OrderStatus> optionalStatus = orderStatusRepository.findByBookingId(bookingId);
		if (!optionalStatus.isPresent()) {
			log.error(String.format(AppConstants.INVALID_BOOKING_ID_EXCEPTION, bookingId));
			throw new InvalidOrderStatusRequestException(AppConstants.INVALID_BOOKING_ID_EXCEPTION + bookingId);
		}
		return convertEntityToDto(optionalStatus.get());
	}

	/**
	 * Gets the track list by service center.
	 *
	 * @param serviceCenterName the service center name
	 * @return the track list by service center
	 */
	@Override
	public List<OrderStatusResponse> getTrackListByServiceCenter(String serviceCenterName) {
		log.info("Started to execute OrderStatusServiceImpl.getTrackListByServiceCenter");
		List<OrderStatusResponse> orderStatusResponses = new ArrayList<>();
		List<OrderStatus> orderStatusList = orderStatusRepository.findByServiceCenterName(serviceCenterName);
		if (!CollectionUtils.isEmpty(orderStatusList)) {
			orderStatusList.forEach(orderStatus -> orderStatusResponses.add(convertEntityToDto(orderStatus)));
		} else {
			log.error(String.format(AppConstants.INVALID_SERVICE_CENTER_EXCEPTION, serviceCenterName));
			throw new InvalidOrderStatusRequestException(
					String.format(AppConstants.INVALID_SERVICE_CENTER_EXCEPTION, serviceCenterName));
		}
		return orderStatusResponses;
	}

	/**
	 * Gets the track list by service center and current status.
	 *
	 * @param serviceCenterName the service center name
	 * @param currentStatus     the current status
	 * @return the track list by service center and current status
	 */
	@Override
	public List<OrderStatusResponse> getTrackListByServiceCenterAndCurrentStatus(String serviceCenterName,
			String currentStatus) {
		log.info("Started to execute OrderStatusServiceImpl.getTrackListByServiceCenterAndCurrentStatus");
		List<OrderStatusResponse> orderStatusResponses = new ArrayList<>();
		List<OrderStatus> orderStatusList = orderStatusRepository
				.findByServiceCenterNameAndCurrentStatus(serviceCenterName, currentStatus);
		if (!CollectionUtils.isEmpty(orderStatusList)) {
			orderStatusList.forEach(orderStatus -> orderStatusResponses.add(convertEntityToDto(orderStatus)));
		} else {
			throw new InvalidOrderStatusRequestException("No tracklist present for the Service center name: "
					+ serviceCenterName + " and current status: " + currentStatus);
		}
		return orderStatusResponses;
	}

	/**
	 * Initiate payment.
	 *
	 * @param bookingId the booking id
	 * @return the response entity
	 */
	@Override
	public ResponseEntity<PaymentResponse> initiatePayment(int bookingId) {
		Optional<OrderStatus> optionalStatus = orderStatusRepository.findByBookingId(bookingId);
		if (!optionalStatus.isPresent()) {
			log.error(String.format(AppConstants.INVALID_BOOKING_ID_EXCEPTION, bookingId));
			throw new InvalidOrderStatusRequestException(AppConstants.INVALID_BOOKING_ID_EXCEPTION + bookingId);
		}
		OrderStatus orderStatus = optionalStatus.get();
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setAmount(orderStatus.getCalculatedCost());
		paymentRequest.setBookingId(orderStatus.getBookingId());
		paymentRequest.setEmail(orderStatus.getCustomerEmail());
		paymentRequest.setServiceCenterName(orderStatus.getServiceCenterName());
		return feignClientPaymentUtil.createStripePayment(paymentRequest);
	}

	/**
	 * Validate request.
	 *
	 * @param orderStatusRequest the order status request
	 * @return true, if successful
	 */
	private boolean validateRequest(OrderStatusRequest orderStatusRequest) {
		if (orderStatusRequest.getBookingId() <= 0) {
			log.error(AppConstants.EMPLTY_BOOKING_ID_EXCEPTION);
			throw new InvalidOrderStatusRequestException(AppConstants.EMPLTY_BOOKING_ID_EXCEPTION);
		}
		if (StringUtils.isEmpty(orderStatusRequest.getTrackStatus())) {
			log.error(AppConstants.EMPLTY_TRACK_STATUS);
			throw new InvalidOrderStatusRequestException(AppConstants.EMPLTY_TRACK_STATUS);
		}
		if (StringUtils.isEmpty(orderStatusRequest.getServiceCenterName())) {
			log.error(AppConstants.EMPLTY_SERVICE_CENTER_NAME);
			throw new InvalidOrderStatusRequestException(AppConstants.EMPLTY_SERVICE_CENTER_NAME);
		}
		if (StringUtils.isEmpty(orderStatusRequest.getCustomerEmail())) {
			log.error(AppConstants.EMPTY_CUSTOMER_EMAIL);
			throw new InvalidOrderStatusRequestException(AppConstants.EMPTY_CUSTOMER_EMAIL);
		}
		if (null == orderStatusRequest.getCurrentStatus()) {
			log.error(AppConstants.EMPLTY_BOOKING_STATUS);
			throw new InvalidOrderStatusRequestException(AppConstants.EMPLTY_BOOKING_STATUS);
		}
		return true;
	}

	/**
	 * Update track status.
	 *
	 * @param orderStatusRequest the order status request
	 * @param bookingId          the booking id
	 * @return the order status response
	 */
	@Override
	public OrderStatusResponse updateTrackStatus(OrderStatusRequest orderStatusRequest, int bookingId) {
		Optional<OrderStatus> optionalStatus = orderStatusRepository.findByBookingId(bookingId);

		if (optionalStatus.isPresent()) {
			log.info("Booking Id already present in database. Hence updating the extsting trackList");
			OrderStatus orderStatus = optionalStatus.get();
			if (orderStatusRequest.getCalculatedCost() > 0) {
				orderStatus.setCalculatedCost(orderStatusRequest.getCalculatedCost());
			}
			if (null != orderStatusRequest.getCurrentStatus()) {
				orderStatus.setCurrentStatus(orderStatusRequest.getCurrentStatus());
			}
			if (null != orderStatusRequest.getTrackStatus()) {
				TrackStatus trackStatus = new TrackStatus();
				trackStatus.setDescription(orderStatusRequest.getTrackStatus());
				trackStatus.setUpdatedOn(LocalDateTime.now());
				List<TrackStatus> trackList = orderStatus.getTrackList();
				trackList.add(trackStatus);
			}
			if (null != orderStatusRequest.getServiceCenterName()) {
				orderStatus.setServiceCenterName(orderStatusRequest.getServiceCenterName());
			}
			if (null != orderStatusRequest.getCustomerEmail()) {
				orderStatus.setCustomerEmail(orderStatusRequest.getCustomerEmail());
			}
			return convertEntityToDto(orderStatusRepository.save(orderStatus));
		} else {
			log.error(String.format(AppConstants.INVALID_BOOKING_ID_EXCEPTION, bookingId));
			throw new InvalidOrderStatusRequestException(AppConstants.INVALID_BOOKING_ID_EXCEPTION + bookingId);
		}
	}

	/**
	 * Checks if is booking id present.
	 *
	 * @param bookingId the booking id
	 * @return true, if is booking id present
	 */
	public boolean isBookingIdPresent(int bookingId) {
		Optional<OrderStatus> optionalStatus = orderStatusRepository.findByBookingId(bookingId);
		return optionalStatus.isPresent();
	}

}
