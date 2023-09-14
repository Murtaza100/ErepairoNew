package com.stackroute.userService.service;

import com.stackroute.userService.collection.Product;
import com.stackroute.userService.config.NotificationMessagePublisher;
import com.stackroute.userService.exception.ResourceNotFoundException;
import com.stackroute.userService.util.AppConstants;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.userService.collection.User;
import com.stackroute.userService.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class UserServiceImpl.
 * @author Murtaza Najmi
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	NotificationMessagePublisher notificationMessagePublisher;

	/**
	 * Get user details by userId
	 * @param id of user
	 * @return User
	 */
	@Override
	public User getUserById(String id) {
		return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user","id",id));
	}

	/**
	 * Update user details by userId
	 * @param id of user
	 * @param user details to be updated
	 * @return updated User and sends notification to user's mail
	 */
	@Override
	public User updateUserById(String id, User user){
		User existingUserDetails = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user","id",id));
//		if(user.getUserId()!=null)
//			existingUserDetails.setUserId(user.getUserId());
//		if(user.getEmailId()!=null)
//			existingUserDetails.setEmailId(user.getEmailId());
		if(user.getContactNo()!=null)
			existingUserDetails.setContactNo(user.getContactNo());
		if(user.getAlternateContactNo()!=null)
			existingUserDetails.setAlternateContactNo(user.getAlternateContactNo());
		if(user.getFirstName()!=null)
			existingUserDetails.setFirstName(user.getFirstName());
		if(user.getLastName()!=null)
			existingUserDetails.setLastName(user.getLastName());
		if(user.getAddress()!=null)
			existingUserDetails.setAddress(user.getAddress());
//		if(user.getPassword()!=null)
//			existingUserDetails.setPassword(user.getPassword());
//		if(user.getProducts()!=null)
//			existingUserDetails.setProducts(user.getProducts());
		User updatedUser = userRepository.save(existingUserDetails);
		notificationMessagePublisher.publishMessage(existingUserDetails.getEmailId(),
				AppConstants.USER_UPDATED_MAIL_SUBJECT, AppConstants.USER_UPDATED_MAIL_BODY);
		return updatedUser;
	}

	/**
	 * Add user details by to DB
	 *
	 * @param user details of user to be added
	 */
	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	/**
	 * Deletes user details by userId
	 * @param id of user
	 * @return response message
	 */
	@Override
	public String deleteUserById(String id) {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user","id",id));
		userRepository.delete(user);
		return AppConstants.API_DELETE_SUCCESS_MESSAGE;
	}

	/**
	 * Add product for user
	 * @param userId of user
	 * @param image of product to be added
	 * @param product details of product to be added
	 * @return User details and sends notification to user's mail
	 */
	@Override
	public User addProductByUserId(String userId, MultipartFile image, Product product) throws IOException {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		List<Product> products = new ArrayList<>();
		if(user.getProducts()!=null)
			products = user.getProducts();
		if(image.getOriginalFilename()!=null){
			product.setImageTitle(image.getOriginalFilename());
			product.setProductImage(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
		}
		products.add(product);
		user.setProducts(products);
		User updatedUser = userRepository.save(user);
		notificationMessagePublisher.publishMessage(user.getEmailId(), AppConstants.PRODUCT_LIST_UPDATED_MAIL_SUBJECT,
				AppConstants.PRODUCT_LIST_UPDATED_MAIL_BODY);
		return updatedUser;
	}

	/**
	 * Update product for user
	 * @param userId of user
	 * @param productId of product to be updated
	 * @param image of product to be updated
	 * @param product details of product to be updated
	 * @return User details and sends notification to user's mail
	 */
	@Override
	public User updateProductByUserIdAndProductId(String userId, String productId, MultipartFile image, Product product) throws IOException {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		List<Product> products = new ArrayList<>();
		if(image !=null && image.getOriginalFilename()!=null){
			product.setImageTitle(image.getOriginalFilename());
			product.setProductImage(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
		}
		products = user.getProducts();
		Product matchedProduct = products.stream()
				.filter(prod -> productId.equals(prod.getId()))
				.findAny()
				.orElseThrow(()-> new ResourceNotFoundException("product","id",productId));

		if(product.getName()!=null)
			matchedProduct.setName(product.getName());
		if(product.getCategory()!=null)
			matchedProduct.setCategory(product.getCategory());
		if(product.getBrand()!=null)
			matchedProduct.setBrand(product.getBrand());
		if(product.getImageTitle()!=null)
			matchedProduct.setImageTitle(product.getImageTitle());
		if(product.getProductImage()!=null)
			matchedProduct.setProductImage(product.getProductImage());

		user.setProducts(products);
		User updatedUser = userRepository.save(user);
		notificationMessagePublisher.publishMessage(user.getEmailId(), AppConstants.PRODUCT_LIST_UPDATED_MAIL_SUBJECT,
				AppConstants.PRODUCT_LIST_UPDATED_MAIL_BODY);
		return updatedUser;
	}

	/**
	 * Deletes product for user
	 * @param userId of user
	 * @param productId of product to be deleted
	 * @return response message and sends notification to user's mail
	 */
	@Override
	public String deleteProductByUserIdAndProductId(String userId, String productId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		List<Product> existingProducts = user.getProducts();
		Product matchedProduct = existingProducts.stream()
				.filter(prod -> productId.equals(prod.getId()))
				.findAny()
				.orElseThrow(()-> new ResourceNotFoundException("product","id",productId));
		existingProducts.remove(matchedProduct);
		userRepository.save(user);
		notificationMessagePublisher.publishMessage(user.getEmailId(), AppConstants.PRODUCT_LIST_UPDATED_MAIL_SUBJECT,
				AppConstants.PRODUCT_LIST_UPDATED_MAIL_BODY);
		return AppConstants.API_DELETE_SUCCESS_MESSAGE;
	}
}
