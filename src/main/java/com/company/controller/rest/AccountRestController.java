package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dto.PrivateAccountDTO;
import com.company.dto.PrivateNewAccountDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Account;
import com.company.service.auth.CustomUserDetails;
import com.company.service.auth.oauth2.OAuth2Account;
import com.company.service.auth.oauth2.OAuth2Authenticator;
import com.company.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRestController {

    private final AccountDAO accountDAO;
    private final IEntityConverter<Account, PrivateAccountDTO> accConverter;
    private final IEntityConverter<Account, PrivateNewAccountDTO> newAccConverter;


    @Autowired
    public AccountRestController(AccountDAO accountDAO,
                                 IEntityConverter<Account, PrivateAccountDTO> accConverter,
                                 IEntityConverter<Account, PrivateNewAccountDTO> newAccConverter) {
        this.accountDAO = accountDAO;
        this.accConverter = accConverter;
        this.newAccConverter = newAccConverter;
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
}
