package com.rile.issuetracker.pages.project;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.pages.Tracker;
import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.security.ProtectedPage;
import com.rile.issuetracker.util.Util;
import java.util.Date;
import javax.annotation.security.RolesAllowed;
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
@ProtectedPage
@RolesAllowed(value = {"Admin", "Moderator", "Architect"})
public class Create {
    
    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form createProjectForm;
    
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String title, description;
    @Property
    private Project project;
    @Property
    @SessionState
    private User loggedInUser;
    @Property
    @Inject
    private ProjectDao projectDao;
    @Inject
    private PageRenderLinkSource renderLinkSource;
    @Property
    private Util util = new Util();
            
    @PageLoaded
    void pageLoaded() {}
    
    Object onActivate() {
        if (loggedInUser.getUsername() == null) {
            return Tracker.class;
        }
        return null;
    }
    
    void onValidateFromCreateProjectForm() {}
    
    @CommitAfter
    Object onSuccessFromCreateProjectForm() {  
        Project newProject = new Project();
        newProject.setUserId(loggedInUser);
        newProject.setTitle(title);
        newProject.setDescription(description);
        newProject.setDateTimeCreated(new Date());
        Project createdProject = projectDao.merge(newProject);
        if (createdProject != null) {
            return renderLinkSource.createPageRenderLinkWithContext("project/View", createdProject.getId());
        }
        return null;
    }
    
    public String getUserAvatar() {
        return loggedInUser.getAvatarPath() == null || loggedInUser.getAvatarPath().isEmpty() ?
                "user-avatar.png" : loggedInUser.getAvatarPath();
    }
    
}
