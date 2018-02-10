package com.company.dao.impl;

import com.company.model.Event;
import org.hibernate.SessionFactory;

public class EventDAOImpl extends DAO<Event> {
    public EventDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
