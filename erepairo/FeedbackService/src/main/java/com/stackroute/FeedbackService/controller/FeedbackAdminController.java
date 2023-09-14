package com.stackroute.FeedbackService.controller;

import com.stackroute.FeedbackService.exception.BookingIdNotFoundException;
import com.stackroute.FeedbackService.exception.FeedbackIdNotFoundException;
import com.stackroute.FeedbackService.exception.ServiceProviderIdNotFoundException;
import com.stackroute.FeedbackService.exception.UserIdNotFoundException;
import com.stackroute.FeedbackService.model.Feedback;
import com.stackroute.FeedbackService.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/feedback/admin")

@RestController
public class FeedbackAdminController {
    @Autowired
    private FeedbackService feedbackService;
    Logger log= LoggerFactory.getLogger(FeedbackAdminController.class);
    @GetMapping("/feedbackbyuserId/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUserId(@PathVariable String userId) throws UserIdNotFoundException
    {
        log.info("started");
        return new ResponseEntity<List<Feedback>>(this.feedbackService.getFeedbackByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/feedbackbybookingId/{bookingId}")
    public ResponseEntity<Feedback> getFeedbackByBookingId(@PathVariable String bookingId) throws BookingIdNotFoundException {
        return new ResponseEntity<Feedback>(this.feedbackService.getFeedbackByBookingId(bookingId), HttpStatus.OK);
    }
    @GetMapping("/feedbackbyfeedbackId/{feedbackId}")
    public Feedback getFeedbackByFeedbackId(@PathVariable String feedbackId) throws FeedbackIdNotFoundException {
        return feedbackService.getByFeedbackId(feedbackId);
    }

    @GetMapping("/feedbackbyserviceproviderId/{spId}")
    public List<Feedback> getFeedbackByServiceProviderId(@PathVariable String spId) throws ServiceProviderIdNotFoundException {
        return feedbackService.getFeedbackByServiceProviderId(spId);
    }
}

