package com.company.dao.impl;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * DAO, который работает с сущностью Account, реализуя основные методы обработки
 */
public class AccountDAOImpl extends DAO<Account> implements AccountDAO {
    private static final Logger LOGGER = Logger.getLogger(AccountDAOImpl.class);

    public AccountDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Account readByEmail(String email) {
        return readByField("email", email);
    }

    @Override
    public Account readByPhoneNumber(Long phoneNumber) {
        return readByField("phoneNumber", phoneNumber);
    }

    @Override
    public Account readByFacebookId(Long id) {
        return readByField("facebookId", id);
    }

    @Override
    public Account readByGoogleId(Long id) {
        return readByField("googleId", id);
    }

    @Override
    public Account readByVkId(Long id) {
        return readByField("vkId", id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Account> readByGroup(int groupId) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Account> accounts = session.createCriteria(GenericReflector.getClassParameterType(this.getClass()))
                    .add(Restrictions.eq("group.id", groupId))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
            return accounts;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }
}
