package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Stefan
 */
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    @Override
    public User userExists(String username, String password) {
        try {
            User user = (User) session.createCriteria(classType)
                    .add(Restrictions.eq("username", username))
                    .add(Restrictions.eq("password", password)).uniqueResult();
            return user != null ? user : null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public boolean emailExists(String email) {
        Long rows = (Long) session.createCriteria(classType)
                .add(Restrictions.eq("email", email))
                .setProjection(Projections.rowCount()).uniqueResult();
        return (rows != 0);
    }

    @Override
    public boolean usernameExists(String username) {
        Long rows = (Long) session.createCriteria(classType)
                .add(Restrictions.eq("username", username))
                .setProjection(Projections.rowCount()).uniqueResult();
        return (rows != 0);
    }
    
}
