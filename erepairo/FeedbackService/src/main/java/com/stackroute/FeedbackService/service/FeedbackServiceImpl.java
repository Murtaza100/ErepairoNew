package com.stackroute.FeedbackService.service;

import com.stackroute.FeedbackService.exception.*;
import com.stackroute.FeedbackService.model.Feedback;
import com.stackroute.FeedbackService.repository.FeedbackRepository;
import com.stackroute.FeedbackService.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    FeedbackRepository feedbackRepository;
    Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    @Override
    public Feedback addFeedback(Feedback feedback) throws InvalidRatingException {
        if(feedback.getRating()<1 || feedback.getRating()>5)
            throw new InvalidRatingException(AppConstants.INVALID_RATING_EXCEPTION);
        else
            return feedbackRepository.save(feedback);
    }




        @Override
        public List<Feedback> getFeedbackByUserId (String userId) throws  UserIdNotFoundException{
            if(feedbackRepository.findByUserId(userId).size()!=0)
                return feedbackRepository.findByUserId(userId);
            else
                throw new UserIdNotFoundException(AppConstants.USER_ID_NOT_FOUND_MESSAGE);
    }


    @Override
    public Feedback getFeedbackByBookingId(String bookingId)  throws BookingIdNotFoundException{
    System.out.println(bookingId);
        if(feedbackRepository.findByBookingId(bookingId)!=null)
            return feedbackRepository.findByBookingId(bookingId);
        else {
            System.out.println("else");
            throw new BookingIdNotFoundException(AppConstants.BOOKING_ID_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public Feedback getByFeedbackId(String feedbackId) throws  FeedbackIdNotFoundException {
        if(feedbackRepository.findByFeedbackId(feedbackId)!=null)
            return feedbackRepository.findByFeedbackId(feedbackId);
        else
            throw new FeedbackIdNotFoundException(AppConstants.FEEDBACK_ID_NOT_FOUND_MESSAGE);
    }

    @Override
    public Feedback updateFeedback(String feedbackId, String userReview) {
        Feedback feedback=feedbackRepository.findByFeedbackId(feedbackId);
        feedback.setUserReview(userReview);
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackByServiceProviderId(String serviceProviderId) throws ServiceProviderIdNotFoundException {
        if(feedbackRepository.findByServiceProviderId(serviceProviderId).size()!=0)
            return feedbackRepository.findByServiceProviderId(serviceProviderId);
        else
            throw  new ServiceProviderIdNotFoundException(AppConstants.SERVICE_PROVIDER_ID_NOT_FOUND_MESSAGE);
    }

    @Override
    public Feedback deleteFeedback(String feedbackId) {
        return feedbackRepository.deleteByFeedbackId(feedbackId);
    }


}
