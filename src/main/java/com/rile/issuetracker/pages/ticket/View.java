package com.rile.issuetracker.pages.ticket;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketComment;
import com.rile.issuetracker.entities.TicketFollower;
import com.rile.issuetracker.entities.TicketPriority;
import com.rile.issuetracker.entities.TicketStatus;
import com.rile.issuetracker.entities.User;
import com.rile.issuetracker.pages.Tracker;
import com.rile.issuetracker.services.dao.ProjectDao;
import com.rile.issuetracker.services.dao.TicketAttachmentDao;
import com.rile.issuetracker.services.dao.TicketCommentDao;
import com.rile.issuetracker.services.dao.TicketDao;
import com.rile.issuetracker.services.dao.TicketFollowerDao;
import com.rile.issuetracker.util.Util;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
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
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author Stefan
 */
public class View {
    
    @Property
    @SessionState
    private User loggedInUser;
    
    @Property
    private Ticket ticket;
    @Property
    @Inject
    private TicketDao ticketDao;
    @Property
    @Inject
    private TicketFollowerDao ticketFollowerDao;
    @Property
    @Inject
    private TicketAttachmentDao ticketAttachmentDao;
    @Property
    private TicketComment ticketCommentP1;
    @Persist
    @Property
    private TicketComment editComment;
    @InjectComponent
    private Form createComment;
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String commentText;
    @Property
    @Inject
    private TicketCommentDao ticketCommentDao;
    
    @Property
    private Project projectP1;
    @Property
    private Project ticketProject;
    @Property
    @Inject
    private ProjectDao projectDao;
    
    @Property
    private TicketStatus statusP1, ticketStatus;
    @Property
    private TicketPriority priorityP1, ticketPriority;
    
    @Inject
    private PageRenderLinkSource renderLinkSource;
    
    @Property
    private Util util = new Util();
    
    public boolean getLoggedIn() {
        return loggedInUser.getEmail() != null;
    }  

    public String getUserAvatar(User user) {
        return user.getAvatarPath() == null || user.getAvatarPath().isEmpty() ?
                "user-avatar.png" : user.getAvatarPath();
    }
    
    public boolean getHasPriviligiesToCRDTicket() {
        return (getLoggedIn() && loggedInUser.getId().equals(ticket.getUserId().getId())) ||
               loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator;
    }
    
    public boolean getHasPriviligiesToCRDComment() {
        return (getLoggedIn() && loggedInUser.getId().equals(ticketCommentP1.getUserId().getId())) ||
               loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator;
    }
    
    public boolean getHasPriviligiesToEditStatusPriority() {
        return loggedInUser.getRole() == Role.Admin || 
               loggedInUser.getRole() == Role.Moderator ||
               loggedInUser.getRole() == Role.Developer ||
               loggedInUser.getRole() == Role.Tester;
    }
    
    @PageLoaded
    void pageLoaded() {}
    
    @Property
    private List<TicketComment> ticketComments;
    
    void onActivate(Integer ticketId) {
        ticket = ticketDao.getByID(ticketId);

        ticketProject = ticket.getProjectId();
        ticketStatus = ticket.getStatus();
        ticketPriority = ticket.getPriority();
       
        ticketComments = ticketCommentDao.loadAllByTicketId(ticketId, false);
        
        updateViews();
    }

    Object onPassivate() {
        return ticket;
    }
    
    @CommitAfter
    public void updateViews() {
        int count = ticket.getViews() + 1;
        ticket.setViews(count);
        ticketDao.merge(ticket);
    }
        
    public TicketFollower getTicketFollower() {
        return ticketFollowerDao.getByUserTicket(loggedInUser.getId(), ticket.getId());
    }
    
    @CommitAfter
    Object onActionFromFollowTicket(Integer ticketId) {
        onActivate(ticketId);
        TicketFollower pf = getTicketFollower();
        if (pf != null) {
            ticketFollowerDao.delete(pf.getId());
        } else {
            pf = new TicketFollower();
            pf.setTicketId(ticket);
            pf.setUserId(loggedInUser);
            ticketFollowerDao.merge(pf);
        }
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticketId);
    }
 
    @CommitAfter
    Object onActionFromDeleteTicket(Integer ticketId) {
        ticketDao.delete(ticketId);
        return Tracker.class;
    }
    
    @CommitAfter
    Object onActionFromSelectProject(Integer ticketId, Project project) {
        onActivate(ticketId);
        ticket.setProjectId(project);
        ticketDao.merge(ticket);
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticketId);
    }
    
    @CommitAfter
    Object onActionFromSelectStatus(Integer ticketId, TicketStatus status) {
        onActivate(ticketId);
        ticket.setStatus(status);
        ticketDao.merge(ticket);
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticketId);
    }
    
    @CommitAfter
    Object onActionFromSelectPriority(Integer ticketId, TicketPriority priority) {
        onActivate(ticketId);
        ticket.setPriority(priority);
        ticketDao.merge(ticket);
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticketId);
    }
    
    void onValidateFromCreateComment() {}
    
    @CommitAfter
    Object onSuccessFromCreateComment() {
        TicketComment comment = null;
        if (editComment != null) {
            editComment.setText(commentText);
            editComment.setDateTimeModified(new Date());
            System.out.println(editComment.getText());
            comment = ticketCommentDao.merge(editComment);
            editComment = null;
        } else {
            TicketComment newComment = new TicketComment();
            newComment.setTicketId(ticket);
            newComment.setUserId(loggedInUser);
            newComment.setText(commentText);
            newComment.setDateTimeCreated(new Date());
            comment = ticketCommentDao.merge(newComment);
        }
        if (comment != null) {
            commentText = "";
            return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticket.getId());
        }
        return null;
    }
    
    @CommitAfter
    Object onActionFromDeleteComment(Integer ticketId, Integer commentId) {
        ticketCommentDao.delete(commentId);
        onActivate(ticketId);
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticket.getId());
    }
    
    @CommitAfter
    Object onActionFromEditComment(Integer commentId) {
        for (TicketComment tc : ticketComments) {
            if (tc.getId().equals(commentId)) {
                editComment = tc;
                commentText = editComment.getText();
            }
        }
        return renderLinkSource.createPageRenderLinkWithContext(this.getClass(), ticket.getId());
    }
    
    public String getSafeText(String htmlText) {
        return Jsoup.clean(htmlText, Whitelist.relaxed());
    }
    
    public String getDatePosted() {
        Date today = new Date();
        Date ticketDate = ticket.getDateTimeModified() != null ? 
                          ticket.getDateTimeModified() : ticket.getDateTimeCreated();
        String type = ticket.getDateTimeModified() != null ? "modified" : "posted";
        if (today.equals(ticketDate)) {
            return type + " today";
        } else if (today.after(ticketDate)) {
            int diff = (int) (today.getTime() - ticketDate.getTime()) / (24 * 60 * 60 * 1000);
            return type + " " + diff + " days ago";
        }
        return "";
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
    
    public String getActiveFor(String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return null;
        }
        switch (parameter) {
            case "userFollowingTicket":
                return getTicketFollower() != null ? "active" : "null";
            default: 
                return null;
        }
    }
    
    public List getStatusEnums() {
        return Arrays.asList(TicketStatus.values());
    }
    
    public List getPriorityEnums() {
        return Arrays.asList(TicketPriority.values());
    }
    
}
