package com.company.dao.impl;

import com.company.dao.api.EventDAO;
import com.company.model.Event;
import com.company.model.Lesson;
import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

public class EventDAOImpl extends DAO<Event> implements EventDAO {
    private static final Logger LOGGER = Logger.getLogger(EventDAOImpl.class);


    public EventDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Event> readAllByGroup(int groupId) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Event> lessons = session.createCriteria(GenericReflector.getClassParameterType(this.getClass()))
                    .add(Restrictions.eq("group.id", groupId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }
}
