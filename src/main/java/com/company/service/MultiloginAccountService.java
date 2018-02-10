package com.company.service;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import com.company.service.util.AccountIdentifyConverter;
import com.company.util.LoginValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class MultiloginAccountService implements UserDetailsService {

    private boolean isNonLocked = true;
    private AccountDAO accountDAO;
    private AccountIdentifyConverter accIdConverter;

    public void setAccIdConverter(AccountIdentifyConverter accIdConverter) {
        this.accIdConverter = accIdConverter;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Данный метод логинит не по username, а по email или номеру телефона
     * Следует найти корректный метод spring security для этой аутентификации
     *
     * @param login
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String login) {
        return new UserDetails() {
            // loadAccount() рекомендуется использовать для получения account
            private transient Account account = null;

            /**
             * Возвращает роли пользователя
             * @return
             */
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                loadAccount(login);
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return loadAccount(login).getPasshash();
            }

            @Override
            public String getUsername() {
                return accIdConverter.IdToName(loadAccount(login).getId());
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                // todo использовать лок для неподтвержденных пользователей
                return isNonLocked;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            private Account loadAccount(String login) {
                if (account == null) {
                    if (LoginValidator.isPhoneNumberValid(login)) {
                        account = accountDAO.readByPhoneNumber(new Long(login));
                    }
                    else if (LoginValidator.isEmailValid(login)) {
                        account = accountDAO.readByEmail(login);
                    }
                    else {
                        throw new UsernameNotFoundException("Username does not match any of the patterns");
                    }

                    // вторая проверка после попытки поиска
                    if (account == null) {
                        throw new UsernameNotFoundException("There is no account with login '" + login);
                    }
                }

                return account;
            }
        };
    }
}
