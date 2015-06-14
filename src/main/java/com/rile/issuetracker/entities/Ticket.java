package com.rile.issuetracker.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "ticket")
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")})
public class Ticket extends AbstractEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TicketPriority priority;
    
    @Column(name = "categories")
    private String categories;
    @Column(name = "views")
    private Integer views;
    @Column(name = "date_time_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeCreated;
    
    @Column(name = "date_time_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeModified;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId")
    private List<TicketFollower> ticketFollowerList;
    
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;
    
    @JoinColumn(name = "ticket_attachment_id", referencedColumnName = "id")
    @ManyToOne
    private TicketAttachment ticketAttachmentId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId")
    private List<TicketComment> ticketCommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticketId")
    private List<TicketAttachment> ticketAttachmentList;

    @Inject
    public Ticket() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Date getDateTimeModified() {
        return dateTimeModified;
    }

    public void setDateTimeModified(Date dateTimeModified) {
        this.dateTimeModified = dateTimeModified;
    }

    public List<TicketFollower> getTicketFollowerList() {
        return ticketFollowerList;
    }

    public void setTicketFollowerList(List<TicketFollower> ticketFollowerList) {
        this.ticketFollowerList = ticketFollowerList;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public TicketAttachment getTicketAttachmentId() {
        return ticketAttachmentId;
    }

    public void setTicketAttachmentId(TicketAttachment ticketAttachmentId) {
        this.ticketAttachmentId = ticketAttachmentId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<TicketComment> getTicketCommentList() {
        return ticketCommentList;
    }

    public void setTicketCommentList(List<TicketComment> ticketCommentList) {
        this.ticketCommentList = ticketCommentList;
    }

    public List<TicketAttachment> getTicketAttachmentList() {
        return ticketAttachmentList;
    }

    public void setTicketAttachmentList(List<TicketAttachment> ticketAttachmentList) {
        this.ticketAttachmentList = ticketAttachmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTitle();
    }
    
}
