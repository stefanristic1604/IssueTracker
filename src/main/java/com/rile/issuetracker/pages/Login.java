package com.rile.issuetracker.pages;

import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.UserDao;
import com.rile.issuetracker.services.security.ProtectedPage;
import javax.annotation.security.RolesAllowed;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;


public class Login {

    
    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form loginForm;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String username, password;
    
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
    
    void onValidateFromLoginForm() {
        User user = userDao.userExists(username, password);
        if (user != null) {
            loggedInUser = user;
        } else {
            alertManager.error("This user account does not exists in our database.");
        }
    }
    
    Object onSuccessFromLoginForm() {
        if (loggedInUser.getUsername() != null) {
            return Tracker.class;
        }
        return null;
    }

}
