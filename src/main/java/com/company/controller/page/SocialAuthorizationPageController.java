package com.company.controller.page;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import com.company.service.auth.IdType;
import com.company.service.auth.IdentifierAccountService;
import com.company.service.auth.oauth2.OAuth2Account;
import com.company.service.auth.oauth2.OAuth2Authenticator;
import com.company.util.HashGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/oauth2")
public class SocialAuthorizationPageController {

    private final IdentifierAccountService accountService;
    private final AccountDAO accountDAO;
    private final OAuth2Authenticator fbAuthenticator;
    private final OAuth2Authenticator vkAuthenticator;


    @Autowired
    public SocialAuthorizationPageController(IdentifierAccountService accountService,
                                             AccountDAO accountDAO,
                                             @Qualifier("facebookOAuth2Authenticator") OAuth2Authenticator fbAuthenticator,
                                             @Qualifier("vkOAuth2Authenticator") OAuth2Authenticator vkAuthenticator) {
        this.accountService = accountService;
        this.accountDAO = accountDAO;
        this.fbAuthenticator = fbAuthenticator;
        this.vkAuthenticator = vkAuthenticator;
    }

    @RequestMapping(value = "/vk", method = RequestMethod.GET)
    public String authenticateUsingVK(@RequestParam(name = "code") String code, HttpServletRequest request) throws IOException {
        OAuth2Account data = vkAuthenticator.readAccountData(code);
        Account account = accountDAO.readByVkId(data.getId());

        if (account == null) {
            account = new Account();
            account.setFirstName(data.getFirstName());
            account.setSecondName(data.getSecondName());
            String password = RandomStringUtils.random(20, true, true);
            account.setPasshash(HashGenerator.generateSHA1(password));
            account.setScheduleNotidication(true);
            account.setSettingsNotification(true);
            account.setVkId(data.getId());
            accountDAO.create(account);
        }

        vkAuthenticator.authenticate(accountService.loadUser(IdType.APP_ID, Integer.toString(account.getId())));
        return "redirect:/";
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String authenticateUsingFacebook(@RequestParam(name = "code") String code, HttpServletRequest request) throws IOException {
        OAuth2Account data = fbAuthenticator.readAccountData(code);
        Account account = accountDAO.readByFacebookId(data.getId());

        if (account == null) {
            account = new Account();
            account.setFirstName(data.getFirstName());
            account.setSecondName(data.getSecondName());
            String password = RandomStringUtils.random(20, true, true);
            account.setPasshash(HashGenerator.generateSHA1(password));
            account.setScheduleNotidication(true);
            account.setSettingsNotification(true);
            account.setFacebookId(data.getId());
            accountDAO.create(account);
        }

        fbAuthenticator.authenticate(accountService.loadUser(IdType.APP_ID, Integer.toString(account.getId())));
        return "redirect:/";
    }
}