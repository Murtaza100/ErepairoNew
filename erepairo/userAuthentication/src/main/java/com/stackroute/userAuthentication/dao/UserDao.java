package com.stackroute.userAuthentication.dao;

/**
 * This is Class UserDao.
 *
 * @author by Zaid
 */

import com.stackroute.userAuthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
}