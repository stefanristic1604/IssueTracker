package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketPriority;
import com.rile.issuetracker.entities.TicketStatus;
import com.rile.issuetracker.entities.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Stefan
 */
public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements TicketDao {

    @Override
    public List<Ticket> getTicketsByProjectID(Integer projectID) {
        return session.createCriteria(classType)
                    .add(Restrictions.eq("projectId.id", projectID))
                    .addOrder(Order.desc("id")).list();
    }
    
    @Override
    public List<Ticket> getTicketsByTitle(String title) {
        return session.createCriteria(classType)
                .add(Restrictions.ilike("title", title + "%"))
                .addOrder(Order.desc("id")).list();
    }

    @Override
    public List<Ticket> loadAllTicketsFromTo(int from) {
        int page = (from - 1) * 10;
        return session.createCriteria(classType)
                .setFirstResult(page).setMaxResults(10).addOrder(Order.asc("id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<Ticket> findlTicketsBy(
            String title, String userData,
            Project project, TicketStatus status, TicketPriority priority) {
        
        Criteria criteria = session.createCriteria(classType);
     
        if (title != null && !title.isEmpty()) { 
            title = title.toLowerCase();
            criteria.add(Restrictions.like("title", title + "%"));
        }
        if (userData != null && !userData.isEmpty()) {
            userData = userData.toLowerCase();
            criteria.add(Restrictions.disjunction()
                .add(Restrictions.like("userId.firstName", userData + "%"))
                .add(Restrictions.like("userId.lastName", userData + "%"))
                .add(Restrictions.like("userId.email", userData + "%"))
                .add(Restrictions.like("userId.username", userData + "%"))
                .add(Restrictions.like("userId.role", userData + "%"))
            );
        }
        if (project != null) {
            criteria.add(Restrictions.eq("projectId.id", project.getId()));
        }
        if (status != null) {
            criteria.add(Restrictions.eq("status", status));
        }
        if (priority != null) {
            criteria.add(Restrictions.eq("priority", priority));
        }
        return criteria.list();
    }
    
}
