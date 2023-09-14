package com.stackroute.userAuthentication.service;



/**
 * This is Class UserService.
 *
 * @author by Zaid
 */

import com.stackroute.userAuthentication.dao.RoleDao;
import com.stackroute.userAuthentication.dao.UserDao;
import com.stackroute.userAuthentication.dto.ServiceProviderDTO;
import com.stackroute.userAuthentication.dto.UserDto;
import com.stackroute.userAuthentication.dto.UserRequest;
import com.stackroute.userAuthentication.entity.Role;
import com.stackroute.userAuthentication.entity.User;
import com.stackroute.userAuthentication.util.AppConstants;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private RoleService roleService;
    
    public void initRoleAndUser() {

//        Role adminRole = new Role();
//        adminRole.setRoleName("Admin new");
//        adminRole.setRoleDescription("Admin role");
//        roleDao.save(adminRole);
//
//        Role userRole = new Role();
//        userRole.setRoleName("User");
//        userRole.setRoleDescription("Default role for newly created record");
//        roleDao.save(userRole);
//        System.out.println("Test");
//
//        User adminUser = new User();
//        adminUser.setUserName("admin123");
//        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
//        adminUser.setUserFirstName("admin");
//        adminUser.setUserLastName("admin");
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        adminUser.setRole(adminRoles);
//        userDao.save(adminUser);
//        System.out.println("Test2");


//        User user = new User();
//        user.setUserName("Zaid123");
//        user.setUserPassword(getEncodedPassword("Zaid@123"));
//        user.setUserFirstName("Zaid");
//        user.setUserLastName("Khan");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public User registerNewUser(UserDto userDto) {
    	//fix here
    	//System.out.println("New");
    	User user = new User();
    	user.setEmail(userDto.getEmail());
    	user.setUserFirstName(userDto.getUserFirstName());
    	user.setUserLastName(userDto.getUserLastName());
    	user.setUserPassword(userDto.getUserPassword());
        Optional<Role> optional = roleDao.findById("User");
        Role role = new Role();
        if(!optional.isPresent()) {
        	
        	role.setRoleName("User");
        	role.setRoleDescription("This is the Customer of the Company");
        	roleService.createNewRole(role);
        	user.setRole(role);
        
        }else {
        	user.setRole(optional.get());
        	role=optional.get();
        }
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        UserRequest userRequest = new UserRequest();
        userRequest.setUserName(userDto.getEmail());
        userRequest.setUserFirstName(userDto.getUserFirstName());
        userRequest.setUserLastName(userDto.getUserLastName());
        userRequest.setUserPassword(userDto.getUserPassword());
        
        com.stackroute.userAuthentication.dto.Role roleDto = new com.stackroute.userAuthentication.dto.Role();
        roleDto.setRoleName(role.getRoleName());
        roleDto.setRoleDescription(role.getRoleDescription());
        userRequest.setRole(roleDto);
        rabbitTemplate.convertAndSend(AppConstants.EXCHANGE,AppConstants.ROUTING_KEY,userRequest);
        
        return userDao.save(user);
    }
    
    public User registerNewServiceProvider(UserDto userDto) {
    	//fix here
    	//System.out.println("New");
    	User user = new User();
    	user.setEmail(userDto.getEmail());
    	user.setUserFirstName(userDto.getUserFirstName());
    	user.setUserLastName(userDto.getUserLastName());
    	user.setUserPassword(userDto.getUserPassword());
        Optional<Role> optional = roleDao.findById("Service Provider");
        Role role = new Role();
        if(!optional.isPresent()) {
        	
        	role.setRoleName("Service Provider");
        	role.setRoleDescription("This is the Service Provider of the Company");
        	roleService.createNewRole(role);
        	user.setRole(role);
        
        }else {
        	user.setRole(optional.get());
        }
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        
        ServiceProviderDTO serviceProviderDTO = new ServiceProviderDTO();
        serviceProviderDTO.setUserFirstName(userDto.getUserFirstName()+" "+userDto.getUserLastName());
        serviceProviderDTO.setEmail(userDto.getEmail());
        serviceProviderDTO.setUserPassword(userDto.getUserPassword());
        rabbitTemplate.convertAndSend(AppConstants.message_EXCHANGE,AppConstants.message_ROUTING_KEY,serviceProviderDTO);

        return userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}