package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.User;

/**
 *
 * @author Stefan
 */
public interface UserDao extends GenericDao<User> {
    
    User userExists(String username, String password);
    boolean emailExists(String email);
    boolean usernameExists(String username);
    
}
