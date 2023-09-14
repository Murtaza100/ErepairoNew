package com.stackroute.ServiceProviderService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.ServiceProviderService.model.ServiceProvider;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * The interface ServiceProviderRepository
 *
 * @author Shivani Pakde
 *
 */
public interface ServiceProviderRepository extends MongoRepository<ServiceProvider, String> {

    Optional<ServiceProvider> findByUniqueId(String uniqueId);

    @Query("{name : ?0, emailId : ?1}")
    Optional<ServiceProvider> getProfileByNameAndEmailId(String name, String emailId);



}
