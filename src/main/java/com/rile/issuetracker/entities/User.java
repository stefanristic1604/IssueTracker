package com.rile.issuetracker.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "avatar_path")
    private String avatarPath;
    @Column(name = "date_time_registered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeRegistered;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<TicketFollower> ticketFollowerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Ticket> ticketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<TicketComment> ticketCommentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<ProjectFollower> projectFollowerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<TicketAttachment> ticketAttachmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Project> projectList;
    
    @Inject
    public User() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Date getDateTimeRegistered() {
        return dateTimeRegistered;
    }

    public void setDateTimeRegistered(Date dateTimeRegistered) {
        this.dateTimeRegistered = dateTimeRegistered;
    }

    public List<TicketFollower> getTicketFollowerList() {
        return ticketFollowerList;
    }

    public void setTicketFollowerList(List<TicketFollower> ticketFollowerList) {
        this.ticketFollowerList = ticketFollowerList;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<TicketComment> getTicketCommentList() {
        return ticketCommentList;
    }

    public void setTicketCommentList(List<TicketComment> ticketCommentList) {
        this.ticketCommentList = ticketCommentList;
    }

    public List<ProjectFollower> getProjectFollowerList() {
        return projectFollowerList;
    }

    public void setProjectFollowerList(List<ProjectFollower> projectFollowerList) {
        this.projectFollowerList = projectFollowerList;
    }

    public List<TicketAttachment> getTicketAttachmentList() {
        return ticketAttachmentList;
    }

    public void setTicketAttachmentList(List<TicketAttachment> ticketAttachmentList) {
        this.ticketAttachmentList = ticketAttachmentList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getUsername();
    }
    
}
