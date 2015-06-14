package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.TicketComment;
import java.util.List;

/**
 *
 * @author Stefan
 */
public interface TicketCommentDao extends GenericDao<TicketComment> {
    
    List<TicketComment> loadAllByTicketId(Integer ticketId, boolean descOrder);
    
}
