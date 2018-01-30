package com.company.service;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

public class AccountService implements UserDetailsService {

    private AccountDAO accountDAO;

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        return new UserDetails() {
            /**
             * Возвращает роли пользователя
             * @return
             */
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Account account = accountDAO.read(name);
                if (account == null) {
                    throw new UsernameNotFoundException("There is no account with nickname '" + name + "' in database");
                }

                return new ArrayList<SimpleGrantedAuthority>();
            }

            @Override
            public String getPassword() {
                Account account = accountDAO.read(name);
                if (account == null) {
                    throw new UsernameNotFoundException("There is no account with nickname '" + name + "' in database");
                }
                return account.getPasshash();
            }

            @Override
            public String getUsername() {
                return name;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
