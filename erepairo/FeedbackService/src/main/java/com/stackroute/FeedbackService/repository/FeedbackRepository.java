package com.stackroute.FeedbackService.repository;

import com.stackroute.FeedbackService.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByUserId(String userId);
    Feedback findByBookingId(String bookingId);
    Feedback findByFeedbackId(String feedbackId);

    List<Feedback> findByServiceProviderId(String serviceProviderId);

    Feedback deleteByFeedbackId(String feedbackId);



}
