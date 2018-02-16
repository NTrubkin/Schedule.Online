package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dto.PrivateAccountDTO;
import com.company.dto.converter.AccountConverter;
import com.company.dto.converter.IEntityConverter;
import com.company.dto.converter.PrivateAccountConverter;
import com.company.model.Account;
import com.company.service.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRestController {

    private final AccountDAO accountDAO;
    private final IEntityConverter<Account, PrivateAccountDTO> accConverter;

    @Autowired
    public AccountRestController(AccountDAO accountDAO, IEntityConverter<Account, PrivateAccountDTO> accConverter) {
        this.accountDAO = accountDAO;
        this.accConverter = accConverter;
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
}
