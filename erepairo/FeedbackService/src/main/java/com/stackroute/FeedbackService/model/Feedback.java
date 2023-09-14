package com.stackroute.FeedbackService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Feedbacks")
public class Feedback {

    @Id
    String feedbackId;
    String userId;
    String bookingId;
    String userReview;


    String serviceProviderId;
    int rating;

    public Feedback() {
    }

    public Feedback(String feedbackId, String userId, String bookingId, String userReview, String serviceProviderId) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.userReview = userReview;
        this.serviceProviderId=serviceProviderId;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
