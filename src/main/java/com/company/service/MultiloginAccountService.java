package com.company.service;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import com.company.service.auth.CustomUser;
import com.company.util.LoginValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class MultiloginAccountService implements UserDetailsService {
    private AccountDAO accountDAO;

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Данный метод логинит не по username, а по login, который может быть email или номером телефона
     *
     * @param login email или номер телефона
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String login) {

        // todo разделить логин по email и по phoneNumber
        Account account = loadAccount(login);

        if (account == null) {
            throw new UsernameNotFoundException("There is no account with login '" + login);
        }

        return new CustomUser(
                account.getId(),
                account.getPasshash(),
                true,
                true,
                true,
                true,       // todo использовать лок для неподтвержденных пользователей
                new ArrayList<>());
    }

    private Account loadAccount(String login) {
        if (LoginValidator.isPhoneNumberValid(login)) {
            return accountDAO.readByPhoneNumber(new Long(login));
        }
        else if (LoginValidator.isEmailValid(login)) {
            return accountDAO.readByEmail(login);
        }
        else {
            throw new UsernameNotFoundException("Login " + login + " does not match any of the patterns");
        }
    }
}

