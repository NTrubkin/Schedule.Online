package com.company.dao.api;

import com.company.model.Account;


/**
 * Определяет методы Hibernate DAO для работы с Account сущностью
 * В том числе и методы общего DAO<T> CRUD + readAll()
 */
public interface AccountDAO extends IDAO<Account> {


    /**
     * Читает объект Account из базы данных с nickname
     *
     * @param nickname никнейм аккаунта в базе данных
     * @return Account сущность, если успех
     */
    Account readByName(String nickname);

    Account readByEmail(String email);

    Account readByPhoneNumber(Long phoneNumber);
}
