package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.Project;
import com.rile.issuetracker.entities.Role;
import com.rile.issuetracker.entities.Ticket;
import com.rile.issuetracker.entities.TicketPriority;
import com.rile.issuetracker.entities.TicketStatus;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
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
    public List<Ticket> findTicketsBy(
            String title, String userData,
            Project project, TicketStatus status, TicketPriority priority) {
        
        Criteria criteria = session.createCriteria(classType);
        
        if (title != null && !title.isEmpty()) { 
            title = title.toLowerCase().trim();
            criteria.add(Restrictions.like("title", "%" + title + "%"));
        }
        if (userData != null && !userData.isEmpty()) {
            userData = userData.toLowerCase().trim();
            criteria.createAlias("userId", "user");
            Disjunction disj = Restrictions.disjunction();
            disj.add(Restrictions.like("user.firstName", "%" + userData + "%"));
            disj.add(Restrictions.like("user.lastName", "%" + userData + "%"));
            disj.add(Restrictions.like("user.email", "%" + userData + "%"));
            disj.add(Restrictions.like("user.username", "%" + userData + "%"));
            for (Role role : Role.values()) {
                if (role.name().contains(userData)) {
                    disj.add(Restrictions.eq("user.role", role));
                } 
            }
            criteria.add(disj);
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
        criteria.addOrder(Order.desc("id"));
        return criteria.list();
    }
    
}
