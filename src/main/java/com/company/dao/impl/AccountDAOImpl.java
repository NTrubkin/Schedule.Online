package com.company.dao.impl;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * DAO, который работает с сущностью Account, реализуя основные методы обработки
 */
public class AccountDAOImpl extends DAO<Account> implements AccountDAO {
    private static final Logger logger = Logger.getLogger(AccountDAOImpl.class);

    /**
     * Читает объект Account из базы данных с nickname
     *
     * @param name никнейм аккаунта в базе данных
     * @return Account сущность, если успех
     */
    public Account read(String name) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("name", name));
            Account account = (Account) criteria.uniqueResult();
            transaction.commit();
            return account;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(HIBERNATE_EXC_MSG);
            throw e;
        } finally {
            session.close();
        }
    }
}
