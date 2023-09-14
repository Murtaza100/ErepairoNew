package com.stackroute.userService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userService.collection.Product;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stackroute.userService.collection.User;
import com.stackroute.userService.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

/**
 * The Class UserController
 * @author Murtaza Najmi
 */

@RestController
@RequestMapping("api/v1")
@RefreshScope
public class UserController {
	org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper mapper;

	/**
	 * Get user by userId
	 * @param id of user
	 * @return response entity
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id){
		return new ResponseEntity<User>(this.userService.getUserById(id), HttpStatus.OK);
	}

	/**
	 * Update user by userId and store in DB
	 * @param id of user
	 * @return response entity
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable("id") String id,@Valid @RequestBody User user)  {
		logger.info("user {}",user);
		return new ResponseEntity<User>(this.userService.updateUserById(id, user),HttpStatus.OK);
	}
	
//	@PostMapping("/user")
//	public ResponseEntity<User> addUser(@RequestBody User user) {
//		return new ResponseEntity<>(this.userService.addUser(user),HttpStatus.OK);
//	}

	/**
	 * Deletes the User
	 * @param id of user
	 * @return the response entity
	 */
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") String id){
		return new ResponseEntity<String>(this.userService.deleteUserById(id),HttpStatus.OK);
	}

	/**
	 * Add the product for User
	 * @param userId of user
	 * @param image of product to be added
	 * @param productData of product to be added
	 * @return Response entity
	 */
	@PostMapping("user/{userId}/product")
	public ResponseEntity<User> addProductByUserId(@PathVariable String userId,
												   @RequestPart("image") MultipartFile image,
												   @RequestParam("productData") String productData)throws IOException{
		Product product = null;
		if(productData!=null){
			product = mapper.readValue(productData,Product.class);
			product.setId(UUID.randomUUID().toString());
		}
		return new ResponseEntity<User>(this.userService.addProductByUserId(userId,image,product),HttpStatus.OK);
	}

	/**
	 * Update the product for User
	 * @param userId of user
	 * @param productId of product
	 * @param image of product to be updated
	 * @param productData of product to be updated
	 * @return Response entity
	 */
	@PutMapping("user/{userId}/product/{productId}")
	public ResponseEntity<User> updateProductByUserIdAndProductId(@PathVariable  String userId, @PathVariable String productId,
																  @RequestPart(required = false) MultipartFile image,
																  @RequestParam String productData) throws  IOException{
		Product product = null;
		if(productData!=null){
			product = mapper.readValue(productData,Product.class);
		}
		return new ResponseEntity<User>(this.userService.updateProductByUserIdAndProductId(userId,productId,image,product),HttpStatus.OK);
	}

	/**
	 * Deletes the product for User
	 * @param userId of user
	 * @param productId of product
	 * @return Response entity
	 */
	@DeleteMapping("/user/{userId}/product/{productId}")
	public ResponseEntity<String> deleteProductByUserIdAndProductId(@PathVariable String userId,@PathVariable String productId){
		return new ResponseEntity<String>(this.userService.deleteProductByUserIdAndProductId(userId, productId),HttpStatus.OK);
	}
}
