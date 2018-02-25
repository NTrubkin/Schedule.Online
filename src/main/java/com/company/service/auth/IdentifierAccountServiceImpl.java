package com.company.service.auth;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import com.company.util.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IdentifierAccountServiceImpl implements UserDetailsService, IdentifierAccountService {

    private final IdType usernameType;
    private final AccountDAO accountDAO;


    @Autowired
    public IdentifierAccountServiceImpl(@Value("LOGIN") IdType usernameType, AccountDAO accountDAO) {
        this.usernameType = usernameType;
        this.accountDAO = accountDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return loadUser(usernameType, username);
    }

    @Override
    public UserDetails loadUser(IdType idType, String id) {
        Account account;

        switch (idType) {
            case APP_ID:
                account = accountDAO.read(new Integer(id));
                break;
            case EMAIL:
                account = accountDAO.readByEmail(id);
                break;
            case PHONE_NUMBER:
                account = accountDAO.readByPhoneNumber(new Long(id));
                break;
            case LOGIN:
                if (LoginValidator.isPhoneNumberValid(id)) {
                    account = accountDAO.readByPhoneNumber(new Long(id));
                }
                else if (LoginValidator.isEmailValid(id)) {
                    account =  accountDAO.readByEmail(id);
                }
                else {
                    throw new UsernameNotFoundException("Login " + id + " does not match any of the patterns");
                }
                break;
            /*
            case FACEBOOK_ID:
                break;
            case GOOGLE_ID:
                break;
            case VK_ID:
                break;
            case TWITTER_ID:
                break;
                */
            default: throw new UnsupportedOperationException("not implemented yet");
        }

        if (account == null) {
            throw new UsernameNotFoundException(String.format("There is no account by id type %s with id %s'", idType, id));
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


}
