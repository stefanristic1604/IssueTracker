package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.TicketFollower;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Stefan
 */
public class TicketFollowerDaoImpl extends GenericDaoImpl<TicketFollower> implements TicketFollowerDao {

    @Override
    public int getFollowerCountByTicketId(Integer ticketId) {
        Long longVal = (Long) session.createCriteria(classType)
                .add(Restrictions.eq("ticketId.id", ticketId))
                .setProjection(Projections.rowCount()).uniqueResult();
        return longVal.intValue();
    }

    @Override
    public TicketFollower getByUserTicket(Integer userId, Integer ticketId) {
        return (TicketFollower) session.createCriteria(classType)
            .add(Restrictions.eq("userId.id", userId))
            .add(Restrictions.eq("ticketId.id", ticketId)).uniqueResult();
    }
    
}
