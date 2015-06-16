package com.rile.issuetracker.pages;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.ProjectFollower;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketFollower;
import com.rile.issuetracker.entities.TicketPriority;
import com.rile.issuetracker.entities.TicketStatus;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.dao.ProjectFollowerDao;
import com.rile.issuetracker.services.dao.TicketDao;
import com.rile.issuetracker.services.dao.TicketFollowerDao;
import com.rile.issuetracker.util.Util;
import java.util.List;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

public class Tracker {
    
    @Property
    @SessionState
    private User loggedInUser;

    @Property
    @Inject
    private ProjectDao projectDao;
    @Property
    private Project projectP1;
    @Property
    private Project project;
    @Property
    private List<Project> projectList;
    @Property
    @Inject
    private ProjectFollowerDao projectFollowerDao;
    
    @Property
    @Inject
    private TicketDao ticketDao;
    @Property
    private List<Ticket> ticketList;
    @Property
    private Ticket ticketP1;
    @Property
    @Inject
    private TicketFollowerDao ticketFollowerDao;

    @InjectComponent
    private Form searchForm;
    
    @Property
    @Persist
    private String searchByTicket, searchByCreator;
    @Property
    @Persist
    private Project searchByProject;
    @Property
    @Persist
    private TicketStatus searchByStatus;
    @Property
    @Persist
    private TicketPriority searchByPriority;
    
    @Inject
    private PageRenderLinkSource renderLinkSource;
    @Property
    private Util util = new Util();
    
    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }    
                
    @PageLoaded
    void onPageLoad() {
        projectList = projectDao.loadAll();
        ticketList = ticketDao.loadAll();
    }
    
    void onActivate() {
        if (project == null) {
            projectList = projectDao.loadAll();
            if (!wasSearchInvoked) {
                ticketList = ticketDao.loadAll();
            } else {
                ticketList = ticketDao.findTicketsBy(
                    searchByTicket, searchByCreator, searchByProject, searchByStatus, searchByPriority
                );
                wasSearchInvoked = false;
            }
        }
    }
    
    void onActivate(Integer projectId) {        
        project = projectDao.getByID(projectId);
        if (!wasSearchInvoked) {
            ticketList = ticketDao.getTicketsByProjectID(project.getId());
        } else {
            ticketList = ticketDao.findTicketsBy(
                searchByTicket, searchByCreator, project, searchByStatus, searchByPriority
            );
            wasSearchInvoked = false;
        }
    }
   
    public ProjectFollower getProjectFollower() {
        return projectFollowerDao.getByUserProject(loggedInUser.getId(), project.getId());
    }
    
    @CommitAfter
    Object onActionFromFollowProject(Integer projectId) {
        onActivate(projectId);
        ProjectFollower pf = getProjectFollower();
        if (pf != null) {
            projectFollowerDao.delete(pf.getId());
        } else {
            pf = new ProjectFollower();
            pf.setProjectId(project);
            pf.setUserId(loggedInUser);
            projectFollowerDao.merge(pf);
        }
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), projectId);
    }
    
    void onValidateFromSearchForm() {}
    
    @Property
    @Persist
    private boolean wasSearchInvoked;
    
    @CommitAfter
    Object onSuccessFromSearchForm() {
        wasSearchInvoked = true;
        return null;
    }

    Object onActionFromSearchClear() {
        searchByTicket = "";
        searchByCreator = "";
        searchByStatus = null;
        searchByPriority = null;
        ticketList = ticketDao.loadAll();
        return null;
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
    
    public TicketFollower getTicketFollower() {
        return ticketFollowerDao.getByUserTicket(loggedInUser.getId(), ticketP1.getId());
    }
        
    public String getActiveFor(String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return null;
        }
        switch (parameter) {
            case "userFollowingProject":
                return getProjectFollower() != null ? "active" : "null";
            case "userFollowingTicket":
                return getTicketFollower() != null ? "anchor-active" : "anchor-inactive";
            default: 
                return null;
        }
    }

    
}
