package com.stackroute.FeedbackService.service;

import com.stackroute.FeedbackService.exception.*;
import com.stackroute.FeedbackService.model.Feedback;


import java.util.List;

public interface FeedbackService {
    Feedback addFeedback(Feedback feedback) throws InvalidRatingException;



     List<Feedback> getFeedbackByUserId(String userId) throws UserIdNotFoundException;

     Feedback getFeedbackByBookingId(String bookingId) throws BookingIdNotFoundException;

    Feedback getByFeedbackId(String feedbackId) throws FeedbackIdNotFoundException;

    Feedback updateFeedback(String feedbackId, String userReview);
     List<Feedback> getFeedbackByServiceProviderId(String serviceProviderId) throws ServiceProviderIdNotFoundException;
      Feedback deleteFeedback(String feedbackId);
}
