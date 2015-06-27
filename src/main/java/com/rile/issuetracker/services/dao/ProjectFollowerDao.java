package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.ProjectFollower;
import java.util.List;

/**
 *
 * @author Stefan
 */
public interface ProjectFollowerDao extends GenericDao<ProjectFollower> {
    
    int getFollowerCountByProjectId(Integer projectId);
    ProjectFollower getByUserProject(Integer userId, Integer projectId);
    List<ProjectFollower> getByUserId(Integer userId);
    
}
