package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.ProjectFollower;
import java.util.List;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Stefan
 */
public class ProjectFollowerDaoImpl extends GenericDaoImpl<ProjectFollower> implements ProjectFollowerDao {

    @Override
    public int getFollowerCountByProjectId(Integer projectId) {
        Long longVal = (Long) session.createCriteria(classType)
                .add(Restrictions.eq("projectId.id", projectId))
                .setProjection(Projections.rowCount()).uniqueResult();
        return longVal.intValue();
    }

    @Override
    public ProjectFollower getByUserProject(Integer userId, Integer projectId) {
        return (ProjectFollower) session.createCriteria(classType)
                .add(Restrictions.eq("userId.id", userId))
                .add(Restrictions.eq("projectId.id", projectId))
                .uniqueResult();
    }

    @Override
    public List<ProjectFollower> getByUserId(Integer userId) {
        return session.createCriteria(classType)
                .add(Restrictions.eq("userId.id", userId)).list();
    }
}
