package com.stackroute.userService.service;

import com.stackroute.userService.collection.Product;
import com.stackroute.userService.collection.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The Interface UserService.
 * @author Murtaza Najmi
 */

public interface UserService {

	User getUserById(String id);

	User updateUserById(String id, User user);

	void addUser(User user);

    String deleteUserById(String id);

    User addProductByUserId(String userId, MultipartFile image, Product product) throws IOException;

	User updateProductByUserIdAndProductId(String userId, String productId, MultipartFile image, Product product) throws IOException;

	String deleteProductByUserIdAndProductId(String userId, String productId);
}
