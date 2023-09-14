package com.stackroute.PaymentService.service;

import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.PaymentService.dto.NotificationRequest;
import com.stackroute.PaymentService.dto.PaymentRequest;
import com.stackroute.PaymentService.dto.PaymentResponse;
import com.stackroute.PaymentService.entity.PaymentDetails;
import com.stackroute.PaymentService.exception.InvalidPaymentRequestException;
import com.stackroute.PaymentService.repository.PaymentRepository;
import com.stackroute.PaymentService.util.AppConstants;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.Mode;

/**
 * The Class PaymentServiceImpl.
 *
 * @author sushanth
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	/** The log. */
	Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	/** The api secret key. */
	@Value("${stripe.key.secret}")
	private String API_SECRET_KEY;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	private RabbitTemplate template;

	/**
	 * Creates the payment sesssion.
	 *
	 * @param stripeRequest the stripe request
	 * @return the stripe response
	 */
	@Override
	public PaymentResponse createPaymentSesssion(PaymentRequest stripeRequest) {
		try {
			log.info("inside PaymentServiceImpl.createPaymentSesssion");
			if (StringUtils.isEmpty(stripeRequest.getEmail())) {
				log.error(AppConstants.EMAIL_EXCEPTION);
				throw new InvalidPaymentRequestException(AppConstants.EMAIL_EXCEPTION);
			} else if (stripeRequest.getAmount() <= 0) {
				log.error(AppConstants.EMAIL_EXCEPTION);
				throw new InvalidPaymentRequestException(AppConstants.EMAIL_EXCEPTION);
			}
			ProductCreateParams productParams = ProductCreateParams.builder().setName(AppConstants.PRODUCT_NAME)
					.setDescription(AppConstants.PRODUCT_DESCRIPTION).setActive(true).build();

			Product product = Product.create(productParams, buildRequestOptions());
			log.info("Product created with productId: " + product.getId());
			PriceCreateParams priceParams = PriceCreateParams.builder().setProduct(product.getId())
					.setUnitAmount(stripeRequest.getAmount() * 100L).setCurrency(AppConstants.CURRENCY_INR)
					.setActive(true).build();

			Price price = Price.create(priceParams, buildRequestOptions());
			log.info("Price created with priceId: " + price.getId());
			SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
					.setSuccessUrl(AppConstants.COMMON_URL + AppConstants.SUCCESS_END_POINT)
					.setCancelUrl(AppConstants.COMMON_URL + AppConstants.CANCEL_END_POINT)
					.addLineItem(SessionCreateParams.LineItem.builder().setPrice(price.getId()).setQuantity(1L).build())
					.setCustomerEmail(stripeRequest.getEmail()).setCurrency(AppConstants.CURRENCY_INR)
					.setMode(Mode.PAYMENT).build();
			Session session = Session.create(sessionCreateParams, buildRequestOptions());
			PaymentResponse stripeResponse = new PaymentResponse();
			stripeResponse.setRedirectUrl(session.getUrl());

			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails.setAmount(stripeRequest.getAmount());
			paymentDetails.setEmail(stripeRequest.getEmail());
			paymentDetails.setBookingId(stripeRequest.getBookingId());
			paymentDetails.setServiceCenterName(stripeRequest.getServiceCenterName());
			paymentDetails.setPaymentInitiatedDate(LocalDateTime.now());
			paymentRepository.save(paymentDetails);

			NotificationRequest notificationRequest = new NotificationRequest();
			notificationRequest.setEmail(stripeRequest.getEmail());
			notificationRequest.setMailSubject(AppConstants.COMPLETE_PAYMENT_NOTIFICATION_MESSAGE);
			if (null != session.getUrl()) {
				notificationRequest.setMailBody(
						String.format(AppConstants.COMPLETE_PAYMENT_NOTIFICATION_MESSAGE_BODY, stripeRequest.getAmount(), session.getUrl()));
			}

			template.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE, AppConstants.NOTIFICATION_ROUTING_KEY,
					notificationRequest);

			return stripeResponse;
		} catch (StripeException stripeException) {
			log.info(AppConstants.STRIPE_EXCEPTION);
			throw new RuntimeException(stripeException.getMessage());
		} catch (InvalidPaymentRequestException invalidPaymentRequestException) {
			throw new InvalidPaymentRequestException(invalidPaymentRequestException.getMessage());
		} catch (Exception exception) {
			log.info(AppConstants.UNKNOWN_EXCEPTION+exception.getMessage());
			throw new InvalidPaymentRequestException(exception.getMessage());
		}
	}

	/**
	 * Builds the request options.
	 *
	 * @return the request options
	 */
	private RequestOptions buildRequestOptions() {
		return RequestOptions.builder().setApiKey(API_SECRET_KEY).build();
	}

}
