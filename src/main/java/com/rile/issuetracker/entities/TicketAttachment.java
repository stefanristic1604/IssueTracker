package com.rile.issuetracker.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "ticket_attachment")
@NamedQueries({
    @NamedQuery(name = "TicketAttachment.findAll", query = "SELECT t FROM TicketAttachment t")})
public class TicketAttachment extends AbstractEntity {

    @Column(name = "file_path")
    private String filePath;
    @OneToMany(mappedBy = "ticketAttachmentId")
    private List<Ticket> ticketList;
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ticket ticketId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    @Inject
    public TicketAttachment() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket ticketId) {
        this.ticketId = ticketId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof TicketAttachment)) {
            return false;
        }
        TicketAttachment other = (TicketAttachment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rile.issuetracker.entities.TicketAttachment[ id=" + id + " ]";
    }
    
}
