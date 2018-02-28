package com.company.dao.impl;

import com.company.dao.api.PermissionDAO;
import com.company.model.Event;
import com.company.model.Permission;
import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PermissionDAOImpl extends DAO<Permission> implements PermissionDAO {
    private static final Logger LOGGER = Logger.getLogger(PermissionDAOImpl.class);

    public PermissionDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Permission> readByGroup(int groupId) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Permission> permissions = session.createCriteria(GenericReflector.getClassParameterType(this.getClass()))
                    .add(Restrictions.eq("groupId", groupId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
            return permissions;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(List<Permission> permissions) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for(Permission permission : permissions) {
                session.update(permission);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Permission readByAccount(int accountId) {
        return readByField("accountId", accountId);
    }
}
