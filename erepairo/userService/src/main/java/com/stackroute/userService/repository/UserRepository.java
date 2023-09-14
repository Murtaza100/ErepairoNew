package com.stackroute.userService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.userService.collection.User;


public interface UserRepository extends MongoRepository<User, String> {

}
