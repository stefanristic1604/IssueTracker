package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.TicketFollower;

/**
 *
 * @author Stefan
 */
public interface TicketFollowerDao extends GenericDao<TicketFollower> {

    int getFollowerCountByTicketId(Integer ticketId);
    TicketFollower getByUserTicket(Integer userId, Integer ticketId);
}
