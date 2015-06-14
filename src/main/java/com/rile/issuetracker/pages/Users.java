package com.rile.issuetracker.pages;

import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.UserDao;
import com.rile.issuetracker.util.Util;
import java.util.List;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 *
 * @author Stefan
 */
public class Users {
    @Property
    @SessionState
    private User loggedInUser;
    @Property
    private User userP1;
    @Property
    private List<User> users;
    @Property
    @Inject
    private UserDao userDao;
    @Inject
    private PageRenderLinkSource renderLinkSource;
    @Property
    private Util util = new Util();

    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }  
    
    public boolean getHasPrivileges() {
        return loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator;
    }
    
    @PageLoaded
    void pageLoaded() {
        users = userDao.loadAll();
    }
        
    Object onActivate() {
        return null;
    }
}
