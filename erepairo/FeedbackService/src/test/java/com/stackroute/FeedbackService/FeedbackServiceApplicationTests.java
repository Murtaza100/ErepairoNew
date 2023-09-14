package com.stackroute.FeedbackService;

import com.stackroute.FeedbackService.exception.BookingIdNotFoundException;
import com.stackroute.FeedbackService.exception.FeedbackIdNotFoundException;
import com.stackroute.FeedbackService.exception.UserIdNotFoundException;
import com.stackroute.FeedbackService.model.Feedback;
import com.stackroute.FeedbackService.repository.FeedbackRepository;
import com.stackroute.FeedbackService.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
class FeedbackServiceApplicationTests {

    /*@Autowired
    private FeedbackService service;

    @MockBean
    private FeedbackRepository repository;
    @Test
    public void addFeedbackTest() {
        Feedback feedback = new Feedback("rrs54656","101", "101", "Good product", "101");
        when(repository.save(feedback)).thenReturn(feedback);
        assertEquals(feedback, service.addFeedback(feedback));
    }

    @Test
    public void deleteFeedbackTest() {
        Feedback feedback = new Feedback("rrs54656","101", "101", "Good product", "101");
        when(repository.deleteByFeedbackId(feedback.getFeedbackId())).thenReturn(feedback);
        assertEquals(feedback, service.deleteFeedback(feedback.getFeedbackId()));
    }

    @Test
    public void feedbackByFeedbackIdTest() throws FeedbackIdNotFoundException {
        Feedback feedback = new Feedback("rrs54656","101", "101", "Good product", "101");
        when(repository.findByFeedbackId(feedback.getFeedbackId())).thenReturn(feedback);
        assertEquals(feedback, service.getByFeedbackId("rrs54656"));
    }

    @Test
    public void feedbackByBookingIdTest() throws BookingIdNotFoundException {
        Feedback feedback = new Feedback("rrs54656","101", "101", "Good product", "101");
        when(repository.findByBookingId(feedback.getBookingId())).thenReturn(feedback);
        assertEquals(feedback, service.getFeedbackByBookingId("101"));
    }*/



}

