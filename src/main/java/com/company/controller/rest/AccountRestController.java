package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dto.AccountDTO;
import com.company.dto.PrivateAccountDTO;
import com.company.dto.PrivateNewAccountDTO;
import com.company.dto.converter.AccountConverter;
import com.company.dto.converter.IEntityConverter;
import com.company.dto.converter.PrivateAccountConverter;
import com.company.model.Account;
import com.company.service.auth.CustomUserDetails;
import com.company.service.auth.oauth2.OAuth2Account;
import com.company.service.auth.oauth2.OAuth2Authenticator;
import com.company.util.HashGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRestController {

    private final AccountDAO accountDAO;
    private final IEntityConverter<Account, PrivateAccountDTO> accConverter;
    private final IEntityConverter<Account, PrivateNewAccountDTO> newAccConverter;
    private final OAuth2Authenticator fbAuthenticator;
    private final OAuth2Authenticator vkAuthenticator;


    @Autowired
    public AccountRestController(AccountDAO accountDAO,
                                 IEntityConverter<Account, PrivateAccountDTO> accConverter,
                                 IEntityConverter<Account, PrivateNewAccountDTO> newAccConverter,
                                 @Qualifier("facebookOAuth2Authenticator") OAuth2Authenticator fbAuthenticator,
                                 @Qualifier("vkOAuth2Authenticator") OAuth2Authenticator vkAuthenticator) {
        this.accountDAO = accountDAO;
        this.accConverter = accConverter;
        this.newAccConverter = newAccConverter;
        this.fbAuthenticator = fbAuthenticator;
        this.vkAuthenticator = vkAuthenticator;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PrivateAccountDTO> readAccount(Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class);
        }

        int id = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(id);
        return new ResponseEntity<>(accConverter.convert(account), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@RequestBody PrivateAccountDTO accountDTO) {
        Account account = accConverter.restore(accountDTO);
        account.setPasshash(accountDAO.read(account.getId()).getPasshash());     // passhash необходимо восстанавливать вручную
        accountDAO.update(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody PrivateNewAccountDTO accountDTO) {
        Account account = newAccConverter.restore(accountDTO);
        accountDAO.create(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/oauth/facebook", method = RequestMethod.POST)
    public ResponseEntity addFacebookAccount(@RequestParam(name = "code") String code, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class);
        }

        int id = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(id);
        OAuth2Account data = fbAuthenticator.readAccountData(code);
        account.setFacebookId(data.getId());
        accountDAO.update(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/oauth/vk", method = RequestMethod.POST)
    public ResponseEntity addVkAccount(@RequestParam(name = "code") String code, Authentication auth) {
        if (!(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalArgumentException("Authentication principal should implement " + CustomUserDetails.class);
        }

        int id = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        Account account = accountDAO.read(id);
        OAuth2Account data = vkAuthenticator.readAccountData(code);
        account.setVkId(data.getId());
        accountDAO.update(account);
        return new ResponseEntity(HttpStatus.OK);
    }
}
