package com.stackroute.BookingService.Repository;

import com.stackroute.BookingService.model.CustomerRequestsDetails;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerRequestsDetails, Integer> {
    @Query(
        value = "select count(*)  from booking_service_model where Cast(preffered_date as date)= :n ",
        nativeQuery = true
    )
    int CountOfRequests(@Param("n") LocalDate date);
    @Query(
            value = "select *  from booking_service_model where email_id= :n order by booked_on desc",
            nativeQuery = true
    )
    List<CustomerRequestsDetails> GetDetailsByEmailID(@Param("n") String email);
    @Query(
            value = "select *  from booking_service_model where mobile_number= :n ",
            nativeQuery = true
    )
    List<CustomerRequestsDetails> GetDetailsByPhnNo(@Param("n") long Phnno);
    @Query(
            value = "select * from  booking_service_model where service_center_id= :n ",
            nativeQuery = true
    )
    List<CustomerRequestsDetails> GetAllBookingsForAServiceCenter(@Param("n") int servicecenterid);
}

