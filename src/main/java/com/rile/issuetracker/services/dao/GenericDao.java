package com.rile.issuetracker.services.dao;

import com.rile.issuetracker.entities.AbstractEntity;
import java.util.List;

/**
 *
 * @author Stefan
 */
public interface GenericDao<T extends AbstractEntity> {

    T persist(T t);
    T getByID(Integer id);
    T merge(T t);
    T delete(Integer id);
    T saveOrUpdate(T t);
    T update(T t);
    List<T> loadAll();
            
}
