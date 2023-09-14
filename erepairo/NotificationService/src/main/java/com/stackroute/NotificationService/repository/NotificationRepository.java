package com.stackroute.NotificationService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.NotificationService.entity.Notification;

/**
 * The Interface NotificationRepository.
 *
 * @author sushanth
 */
public interface NotificationRepository extends MongoRepository<Notification, Long> {

	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @return the optional
	 */
	Optional<Notification> findByEmail(String email);
}
