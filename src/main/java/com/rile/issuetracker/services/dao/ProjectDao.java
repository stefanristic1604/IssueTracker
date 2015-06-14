package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.Project;

/**
 *
 * @author Stefan
 */
public interface ProjectDao extends GenericDao<Project> {
    
    Project getByTitle(String title);
    
}
