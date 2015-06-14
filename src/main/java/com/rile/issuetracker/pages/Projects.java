package com.rile.issuetracker.pages;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.dao.ProjectFollowerDao;
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
public class Projects {

    @Property
    @SessionState
    private User loggedInUser;
    @Property
    private Project projectP1;
    @Property
    private List<Project> projects;
    @Property
    @Inject
    private ProjectDao projectDao;
    @Property
    @Inject
    private ProjectFollowerDao projectFollowerDao;
    @Inject
    private PageRenderLinkSource renderLinkSource;
    @Property
    private Util util = new Util();
    
    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }  
    
    public boolean getHasPrivileges() {
        return loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator ||
               loggedInUser.getRole() == Role.Architect;
    }
    
    @PageLoaded
    void pageLoaded() {
        projects = projectDao.loadAll();
    }
        
    Object onActivate() {
        return null;
    }
}
