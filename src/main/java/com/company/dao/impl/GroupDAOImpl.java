package com.company.dao.impl;

import com.company.dao.api.GroupDAO;
import com.company.model.Group;
import org.hibernate.SessionFactory;

public class GroupDAOImpl extends DAO<Group> implements GroupDAO {
    public GroupDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
