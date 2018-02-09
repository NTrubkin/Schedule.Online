package com.company.dao.impl;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

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
}
