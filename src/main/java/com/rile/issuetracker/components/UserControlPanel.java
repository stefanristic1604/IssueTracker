package com.rile.issuetracker.components;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.ProjectFollower;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketFollower;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.dao.ProjectFollowerDao;
import com.rile.issuetracker.services.dao.TicketDao;
import com.rile.issuetracker.services.dao.TicketFollowerDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Stefan
 */
public class UserControlPanel {

    @Property @SessionState
    private User loggedInUser;

    @Property
    private Ticket ticketPostedP1;
    @Property
    private List<Ticket> postedTickets;
    @Property @Inject
    private TicketDao ticketDao;
    
    @Property
    private Ticket ticketFollowerP1;
    @Property
    private List<Ticket> followingTickets;
    @Property @Inject
    private TicketFollowerDao ticketFollowerDao;
   
    @Property
    private Project projectFollowerP1;
    @Property
    private List<Project> followingProjects;
    @Property @Inject
    private ProjectDao projectDao;
    @Property @Inject
    private ProjectFollowerDao projectFollowerDao;
       
    @SetupRender
    void setupRender() {
        postedTickets = ticketDao.getTicketsPostedByUserID(loggedInUser.getId());
        if (followingTickets == null)
            followingTickets = new ArrayList<Ticket>();
        for (TicketFollower tf : ticketFollowerDao.getByUserId(loggedInUser.getId())) {
            Ticket t = ticketDao.getByID(tf.getTicketId().getId());
            if (t != null) {
                followingTickets.add(t);
            }
        }
        if (followingProjects == null)
            followingProjects = new ArrayList<Project>();
        for (ProjectFollower pf : projectFollowerDao.getByUserId(loggedInUser.getId())) {
            Project p = projectDao.getByID(pf.getProjectId().getId());
            if (p != null) {
                followingProjects.add(p);
            }
        }
    }

    @CommitAfter
    void onActionFromDeleteTicket(int ticketId) {
        ticketDao.delete(ticketId);
    }

    public TicketFollower getTicketFollower(Ticket ticket) {
        return ticketFollowerDao.getByUserTicket(loggedInUser.getId(), ticket.getId());
    }
    
    @CommitAfter
    void onActionFromFollowTicket(Ticket ticket) {
        TicketFollower tf = getTicketFollower(ticket);
        if (tf != null) {
            ticketFollowerDao.delete(tf.getId());
        } else {
            tf = new TicketFollower();
            tf.setTicketId(ticket);
            tf.setUserId(loggedInUser);
            ticketFollowerDao.merge(tf);
        }
    }
    
    public ProjectFollower getProjectFollower(Project project) {
        return projectFollowerDao.getByUserProject(loggedInUser.getId(), project.getId());
    }
    
    @CommitAfter
    void onActionFromFollowProject(Project project) {
        ProjectFollower pf = getProjectFollower(project);
        if (pf != null) {
            projectFollowerDao.delete(pf.getId());
        } else {
            pf = new ProjectFollower();
            pf.setProjectId(project);
            pf.setUserId(loggedInUser);
            projectFollowerDao.merge(pf);
        }
    }
    
    @CommitAfter
    void onActionFromDeleteProject(int projectId) {
        projectDao.delete(projectId);
    }
    
    public List<String> getProjectTablePrivileges() {
        List<String> privilieges = new ArrayList<String>();
        privilieges.add("follow");
        
        if (loggedInUser.getRole() == Role.Architect || 
            loggedInUser.getRole() == Role.Moderator || 
            loggedInUser.getRole() == Role.Admin) {
                privilieges.add("edit");
                privilieges.add("delete");
        }
        return privilieges;
    }
    
}
