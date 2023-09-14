package com.stackroute.userService;

import com.stackroute.userService.collection.Address;
import com.stackroute.userService.collection.Product;
import com.stackroute.userService.collection.Role;
import com.stackroute.userService.collection.User;
import com.stackroute.userService.config.NotificationMessagePublisher;
import com.stackroute.userService.repository.UserRepository;
import com.stackroute.userService.service.UserService;
import com.stackroute.userService.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    NotificationMessagePublisher notificationMessagePublisher;

    // Test getUserById
    @Test
    void getUserByIdTest() {
        String userId = "john@gmail.com";
        Address address = new Address("gola road","sadar bazar",
                "patna","bihar","india",801505);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Role role= new Role("Customer","Customer");
        Optional<User> user = Optional.of(new User(userId,"John","Doe", "123",
                "8887656765","","john@gmail.com",addresses, null ,role));
        when(userRepository.findById(userId)).thenReturn(user);
        assertEquals(userId, userService.getUserById(userId).getUserId());
    }

    //Test updateUserById
    @Test
    void updateUserByIdTest() {
        String userId = "john@gmail.com";
        Address address = new Address("gola road","sadar bazar",
                "patna","bihar","india",801505);
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        Role role= new Role("Customer","Customer");
        User user = new User(userId,"John","Doe", "123",
                "8887656765","","john@gmail.com",addresses, null ,role);
        User updatedUser = new User(userId,"John","Doe", "123",
                "9999765676","","john@gmail.com",addresses, null ,role);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(updatedUser);
        assertEquals("9999765676", userService.updateUserById(userId,updatedUser).getContactNo());
    }

    // Test addUser
    @Test
    void addUser() {
        String userId = "john@gmail.com";
        Role role= new Role("Customer","Customer");
        User user = new User(userId,"John","Doe", "123",
                null,null,"john@gmail.com",null, null ,role);
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(userId, user.getEmailId());
    }

    // Test deleteUserById
    @Test
    void deleteUserById() {
        String userId = "john@gmail.com";
        Role role= new Role("Customer","Customer");
        Optional<User> user = Optional.of(new User(userId,"John","Doe", "123",
                null,null,"john@gmail.com",null, null ,role));
        when(userRepository.findById(userId)).thenReturn(user);
        assertEquals(AppConstants.API_DELETE_SUCCESS_MESSAGE, userService.deleteUserById(userId));
    }

    // Test addProductById
    @Test
    void addProductByUserIdTest() throws IOException {
        String userId = "john@gmail.com";
        Role role= new Role("Customer","Customer");
        User user = new User(userId,"John","Doe", "123",
                "8887656765","","john@gmail.com",null, null ,role);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        String productId = UUID.randomUUID().toString();
        Product product = new Product(productId,"realme c15","mobile","realme",null,null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        MockMultipartFile image
                = new MockMultipartFile(
                "image",
                "mobile.png".getBytes()
        );
        User updatedUser = new User(userId,"John","Doe", "123",
                "9999765676","","john@gmail.com",null, products ,role);
        when(userRepository.save(any())).thenReturn(updatedUser);
        assertEquals(products, userService.addProductByUserId(userId,image,product).getProducts());
    }

    // Test updateProductById
    @Test
    void updateProductByUserIdAndProductId() throws IOException {
        MockMultipartFile image
                = new MockMultipartFile(
                "image",
                "mobile.png".getBytes()
        );
        String userId = "john@gmail.com";
        String productId = UUID.randomUUID().toString();
        Product product = new Product(productId,"realme c15","mobile","realme",null,null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Role role= new Role("Customer","Customer");
        User user = new User(userId,"John","Doe", "123",
                "8887656765","","john@gmail.com",null, products ,role);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Product updatedProduct = new Product(productId,"dell vostro","laptop","dell",null,null);
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(products, userService.updateProductByUserIdAndProductId(userId,productId,image,updatedProduct).getProducts());
    }

    // Test deleteProduct
    @Test
    void deleteProductByUserIdAndProductId() {
        String userId = "john@gmail.com";
        String productId = UUID.randomUUID().toString();
        Product product = new Product(productId,"realme c15","mobile","realme",null,null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        Role role= new Role("Customer","Customer");
        User user = new User(userId,"John","Doe", "123",
                "8887656765","","john@gmail.com",null, products ,role);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertEquals(AppConstants.API_DELETE_SUCCESS_MESSAGE, userService.deleteProductByUserIdAndProductId(userId,productId));
    }
}
