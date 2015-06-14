package com.rile.issuetracker.pages.ticket;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketPriority;
import com.rile.issuetracker.entities.TicketStatus;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.pages.Tracker;
import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.dao.TicketDao;
import com.rile.issuetracker.util.Util;
import java.util.Date;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
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
    private Form editTicketForm;
    
    @Persist
    @Property
    private Ticket ticketToEdit;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String title, description;
    @Property
    @Persist
    private Project selectProject;
    @Property
    private TicketStatus ticketStatus;
    @Property
    private TicketPriority ticketPriority;
    @Property
    @SessionState
    private User loggedInUser;
    @Property
    @Inject
    private TicketDao ticketDao;
    @Property
    @Inject
    private ProjectDao projectDao;
    @Inject
    private PageRenderLinkSource renderLinkSource;
    @Property
    private Util util = new Util();
            
    @PageLoaded
    void pageLoaded() {
        ticketStatus = TicketStatus.Open;
        ticketPriority = TicketPriority.Not_Assigned;
    }
    
    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }  

    public boolean getHasPriviligiesToCRD() {
        return (getLoggedIn() && loggedInUser.getId().equals(ticketToEdit.getUserId().getId())) ||
               loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator;
    }
    
    public boolean getHasTicketControlPrivileges() {
        return loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator ||
               loggedInUser.getRole() == Role.Developer ||
               loggedInUser.getRole() == Role.Tester;
    }
    
    Object onActivate(Integer ticketId) {
        ticketToEdit = ticketDao.getByID(ticketId);
        if (!getHasPriviligiesToCRD()) {
            return renderLinkSource.createPageRenderLinkWithContext("ticket/View", ticketToEdit.getId());
        }
        title = ticketToEdit.getTitle();
        description = ticketToEdit.getDescription();
        selectProject = ticketToEdit.getProjectId();
        ticketStatus = ticketToEdit.getStatus();
        ticketPriority = ticketToEdit.getPriority();
        return null;
    }
    
    void onValidateFromEditTicketForm() {
        String alertMsgForSelect = "";
        if (selectProject == null) {
            alertMsgForSelect += "project; ";
        }
        if (ticketStatus == null) {
            alertMsgForSelect += "status; ";
        }
        if (ticketPriority == null) {
            alertMsgForSelect += "priority; ";
        }
        if (!alertMsgForSelect.isEmpty()) {
            alertManager.error("Select " + alertMsgForSelect);
        }
    }
    
    @CommitAfter
    Object onSuccessFromEditTicketForm() {  
        ticketToEdit.setTitle(title);
        ticketToEdit.setDescription(description);
        ticketToEdit.setProjectId(selectProject);
        ticketToEdit.setStatus(ticketStatus);
        ticketToEdit.setPriority(ticketPriority);
        ticketToEdit.setCategories(selectProject.getTitle());
        ticketToEdit.setDateTimeModified(new Date());
        Ticket modified = ticketDao.merge(ticketToEdit);
        if (modified != null) {
            return renderLinkSource.createPageRenderLinkWithContext("ticket/View", modified.getId());
        }
        return null;
    }
    
    
    Object onPassivate() {
        if (ticketToEdit == null) {
            return Tracker.class;
        }
        return ticketToEdit;
    }
    
    @CommitAfter
    Object onActionFromDeleteTicket(Integer ticketId) {
        ticketDao.delete(ticketId);
        return Tracker.class;
    }
    
    public ValueEncoder getEncoder() {
        return new ValueEncoder<Project>() {
            @Override
            public String toClient(Project v) {
                return String.valueOf(v.getId());
            }
            @Override
            public Project toValue(String string) {
                return projectDao.getByID(Integer.parseInt(string));
            }
        };
    }
    
    public String getUserAvatar(User user) {
        return user.getAvatarPath() == null || user.getAvatarPath().isEmpty() ?
                "user-avatar.png" : user.getAvatarPath();
    }
    
    
}
