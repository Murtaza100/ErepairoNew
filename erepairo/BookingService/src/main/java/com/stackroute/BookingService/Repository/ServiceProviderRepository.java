package com.stackroute.BookingService.Repository;

import com.stackroute.BookingService.model.ServiceCenterDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ServiceProviderRepository extends MongoRepository<ServiceCenterDetails, Integer> {
    @Query("{_id : ?0}")                                         // SQL Equivalent : SELECT * FROM BOOK where author = ?
    ServiceCenterDetails getbyid(int id);



   // @Query("{ServiceCenterLocation: { $regex : /?0/i}}")
    @Query(value = "{'ServiceCenterLocation': {$regex : ?0, $options: 'i'}}")
    List<ServiceCenterDetails> GetByLocationName(String val);

    @Query(value= "{_id : ?0}", fields="{OpensAt:1, CloseAt:1}")
    ServiceCenterDetails GetClosingAndOpeningTime(int id);


}

