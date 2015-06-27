package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.Project;
import java.util.List;

/**
 *
 * @author Stefan
 */
public interface ProjectDao extends GenericDao<Project> {
    
    Project getByTitle(String title);
    List<Project> getProjectsCreatedByUserID(Integer userId);
    
}
