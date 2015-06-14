package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketPriority;
import com.rile.issuetracker.entities.TicketStatus;
import java.util.List;

/**
 *
 * @author Stefan
 */
public interface TicketDao extends GenericDao<Ticket> {
    
    List<Ticket> getTicketsByProjectID(Integer projectID);
    List<Ticket> getTicketsByTitle(String title); 
    List<Ticket> loadAllTicketsFromTo(int from);
    List<Ticket> findlTicketsBy(
        String title, String userData, 
        Project project, TicketStatus status, TicketPriority priority
    );
}
