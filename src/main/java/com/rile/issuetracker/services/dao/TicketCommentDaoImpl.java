package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.TicketComment;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Stefan
 */
public class TicketCommentDaoImpl extends GenericDaoImpl<TicketComment> implements TicketCommentDao {

    @Override
    public List<TicketComment> loadAllByTicketId(Integer ticketId, boolean descOrder) {
        return session.createCriteria(classType)
                .add(Restrictions.eq("ticketId.id", ticketId))
                .addOrder(descOrder ? Order.desc("id") : Order.asc("id"))
                .list();
    }

}
