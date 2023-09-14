package com.stackroute.FeedbackService.controller;

import com.stackroute.FeedbackService.exception.BookingIdNotFoundException;
import com.stackroute.FeedbackService.exception.FeedbackIdNotFoundException;
import com.stackroute.FeedbackService.exception.InvalidRatingException;
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

@RequestMapping("/api/feedback/user")
@RestController

public class FeedbackUserController {

    @Autowired
    private FeedbackService feedbackService;
    Logger log= LoggerFactory.getLogger(FeedbackUserController.class);

    @PostMapping("/addfeedback")
    public ResponseEntity<Feedback> provideFeedback(@RequestBody Feedback feedback) throws InvalidRatingException {
        log.info("Feedback ");
        return new ResponseEntity<Feedback>(this.feedbackService.addFeedback(feedback), HttpStatus.OK);
    }
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

    @PutMapping("/updatefeedback/{feedbackId}/{userReview}")
    public Feedback updateFeedback(@PathVariable("feedbackId") String feedbackId, @PathVariable("userReview") String userReview)
    {
        return feedbackService.updateFeedback(feedbackId, userReview);
    }

    @DeleteMapping("/deletefeedback/{feedbackId}")
    public Feedback deleteFeedback(@PathVariable String feedbackId)
    {
        return feedbackService.deleteFeedback(feedbackId);
    }
}
