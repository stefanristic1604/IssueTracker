package com.rile.issuetracker.pages;

import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.UserDao;
import java.util.Date;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Stefan
 */
public class Register {
    
    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form registerForm;
    
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String firstName, lastName, username,
                   email, password, confirmPassword;
    
    @SessionState
    private User loggedInUser;
    @Inject
    private UserDao userDao;
    
    @PageLoaded
    void onPageLoad() {}
    
    Object onActivate() {
        if (loggedInUser.getUsername() != null) {
            return Tracker.class;
        }
        return null;
    }
    
    void onValidateFromRegisterForm() {
        if (userDao.emailExists(email)) {
            alertManager.error("Email is already in use");
        }
        if (userDao.usernameExists(username)) {
            alertManager.error("Username is already in use");
        }   
        if (!password.equals(confirmPassword)) {
            alertManager.error("Your passwords does not match");
        }
    }
    
    @CommitAfter
    Object onSuccessFromRegisterForm() {
        User registerUser = new User();
        registerUser.setFirstName(firstName);
        registerUser.setLastName(lastName);
        registerUser.setEmail(email);
        registerUser.setUsername(username);
        registerUser.setPassword(password);
        registerUser.setRole(Role.Member);
        registerUser.setAvatarPath("user-avatar.png");
        registerUser.setDateTimeRegistered(new Date());
        loggedInUser = userDao.merge(registerUser);
        
        if (loggedInUser.getUsername() != null) {
            return Tracker.class;
        }
        return null;
    }
    
}
