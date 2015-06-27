package com.rile.issuetracker.pages.user;

import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.pages.Tracker;
import com.rile.issuetracker.services.dao.UserDao;
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
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author Stefan
 */
public class Edit {

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form editForm;

    @Property @Persist
    private User editUser;
            
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String firstName, lastName, username,
            email, password, confirmPassword;

    @SessionState
    private User loggedInUser;
    @Inject
    private UserDao userDao;
    @Inject
    private PageRenderLinkSource renderLinkSource;
    
    @PageLoaded
    void onPageLoad() {
    }

    Object onActivate(int userId) {
        if (loggedInUser.getUsername() == null || loggedInUser.getId() != userId) {
            return Tracker.class;
        }
        editUser = userDao.getByID(userId);
        firstName = editUser.getFirstName();
        lastName = editUser.getLastName();
        username = editUser.getUsername();
        email = editUser.getEmail();
        password = editUser.getPassword();
        return null;
    }

    Object onPassivate() {
        if (editUser == null) {
            return Tracker.class;
        }
        return editUser;
    }
    
    void onValidateFromEditForm() {
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
    Object onSuccessFromEditForm() {
        editUser.setFirstName(firstName);
        editUser.setLastName(lastName);
        editUser.setEmail(email);
        editUser.setUsername(username);
        editUser.setPassword(password);
        User modified = userDao.merge(editUser);
        if (modified != null) {
            return renderLinkSource.createPageRenderLinkWithContext("user/View", modified.getId());
        }
        return null;
    }
}
