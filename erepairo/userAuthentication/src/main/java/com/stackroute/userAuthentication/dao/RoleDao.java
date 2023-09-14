package com.stackroute.userAuthentication.dao;

/**
 * This is Class RoleDao.
 *
 * @author by Zaid
 */

import com.stackroute.userAuthentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {

}