package com.rile.issuetracker.components;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.ProjectDao;
import java.util.List;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

//@Import(stylesheet = "context:mybootstrap/css/bootstrap.css")
public class Layout {

    @Inject
    private ComponentResources resources;

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    @SessionState
    private User loggedInUser;
    
    @Property
    @Inject
    private ProjectDao projectDao;
    @Property 
    private Project project;
    @Property
    private List<Project> projectList;
    
    @SetupRender
    void setupRender() {
        if (projectList == null) {
            projectList = projectDao.loadAll();
        }
    }
    
    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }
    
    public void onActionFromLogout() {
        loggedInUser = null;
    }
    
    public void onActionFromChooseLanguage() {
        
    }
            
    public boolean getHasPermissionToCreateNewProject() {
        return loggedInUser.getRole() == Role.Admin ||
               loggedInUser.getRole() == Role.Moderator ||
               loggedInUser.getRole() == Role.Architect;
    }
    
}
