package com.company.dao.impl;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import org.apache.log4j.Logger;

/**
 * DAO, который работает с сущностью Account, реализуя основные методы обработки
 */
public class AccountDAOImpl extends DAO<Account> implements AccountDAO {
    private static final Logger LOGGER = Logger.getLogger(AccountDAOImpl.class);


    /**
     * Читает объект Account из базы данных с nickname
     *
     * @param name никнейм аккаунта в базе данных
     * @return Account сущность, если успех
     */
    public Account readByName(String name) {
        return readByField("name", name);
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
