package com.stackroute.userAuthentication.service;

/**
 * This is Class RoleService.
 *
 * @author by Zaid
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.userAuthentication.dao.RoleDao;
import com.stackroute.userAuthentication.entity.Role;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}