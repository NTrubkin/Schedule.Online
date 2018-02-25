package com.company.dao.api;

import com.company.model.Account;

import java.util.List;


/**
 * Определяет методы Hibernate DAO для работы с Account сущностью
 * В том числе и методы общего DAO<T> CRUD + readAll()
 */
public interface AccountDAO extends IDAO<Account> {

    Account readByEmail(String email);

    Account readByPhoneNumber(Long phoneNumber);

    Account readByFacebookId(Long id);
    Account readByGoogleId(Long id);
    Account readByVkId(Long id);

    List<Account> readByGroup(int groupId);
}
