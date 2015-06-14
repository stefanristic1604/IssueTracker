package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.Project;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Stefan
 */
public class ProjectDaoImpl extends GenericDaoImpl<Project> implements ProjectDao {

    @Override
    public Project getByTitle(String title) {
        return (Project) session.createCriteria(classType)
                .add(Restrictions.eq("title", title)).uniqueResult();
    }
    
}
