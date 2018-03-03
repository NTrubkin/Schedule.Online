package com.company.controller.rest;

import com.company.dao.api.AccountDAO;
import com.company.dto.ErrorDTO;
import com.company.dto.PrivateAccountDTO;
import com.company.dto.PrivateNewAccountDTO;
import com.company.dto.converter.IEntityConverter;
import com.company.model.Account;
import com.company.util.CommonValidator;
import com.company.util.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRestController {

    private static final String NOT_FOUND_MSG = "аккаунт не найден";
    private static final String PHONE_ALREADY_EXISTS_MSG = "есть другой аккаунт с таким номером телефона";
    private static final String EMAIL_ALREADY_EXISTS_MSG = "есть другой аккаунт с таким email";
    private static final String FACEBOOK_ALREADY_EXISTS_MSG = "есть другой аккаунт с привязкой к этому Facebook аккаунту";
    private static final String VK_ALREADY_EXISTS_MSG = "есть другой аккаунт с привязкой к этому VK аккаунту";
    private static final String INVALID_FIRST_NAME_MSG = "неверное имя аккаунта";
    private static final String INVALID_SECOND_NAME_MSG = "неверная фамилия аккаунта";
    private static final String INVALID_SCHEDULE_NOT_MSG = "нет настройки получения уведомлений расписания";
    private static final String INVALID_SETTINGS_NOT_MSG = "нет настройки получения уведомлений настроек группы";
    private static final String IDENTIFIER_REQ_MSG = "требуется хотя бы один уникальный идентификатор (email, номер телефона, facebook, vk)";
    private static final String LOGIN_REQ_MSG = "требуется email или номер телефона";
    private static final String INVALID_PASS_MSG = "неверный пароль аккаунта (от %s до %s символов)";
    private static final String INVALID_EMAIL_MSG = "неверный email аккаунта";
    private static final String INVALID_PHONE_MSG = "неверный номер телефона аккаунта";

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

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateAccount(@RequestBody PrivateAccountDTO accountDTO) {
        ErrorDTO errorDTO = new ErrorDTO();

        if (accountDAO.read(accountDTO.getId()) == null) {
            errorDTO.addMessage(NOT_FOUND_MSG);
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }

        if (checkAccountFields(accountDTO, errorDTO)) {
            Account account = accConverter.restore(accountDTO);
            account.setPasshash(accountDAO.read(account.getId()).getPasshash());     // passhash необходимо восстанавливать вручную
            accountDAO.update(account);
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody PrivateNewAccountDTO accountDTO) {
        ErrorDTO errorDTO = new ErrorDTO();
        if(checkNewAccountFields(accountDTO, errorDTO)) {
            Account account = newAccConverter.restore(accountDTO);
            accountDAO.create(account);
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkAccountFields(PrivateAccountDTO accountDTO, ErrorDTO errorDTO) {
        if(accountDTO.getEmail() != null) {
            if (LoginValidator.isEmailValid(accountDTO.getEmail())) {
                Account byEmail = accountDAO.readByEmail(accountDTO.getEmail());
                if (byEmail != null && !byEmail.getId().equals(accountDTO.getId())) {
                    errorDTO.addMessage(EMAIL_ALREADY_EXISTS_MSG);
                }
            }
            else {
                errorDTO.addMessage(INVALID_EMAIL_MSG);
            }
        }

        if(accountDTO.getPhoneNumber() != null) {
            if (LoginValidator.isPhoneNumberValid(accountDTO.getPhoneNumber().toString())) {
                Account byPhone = accountDAO.readByPhoneNumber(accountDTO.getPhoneNumber());
                if (byPhone != null && !byPhone.getId().equals(accountDTO.getId())) {
                    errorDTO.addMessage(PHONE_ALREADY_EXISTS_MSG);
                }
            }
            else {
                errorDTO.addMessage(INVALID_PHONE_MSG);
            }
        }

        Account byFacebook = accountDAO.readByFacebookId(accountDTO.getFacebookId());
        if (byFacebook != null && !byFacebook.getId().equals(accountDTO.getId())) {
            errorDTO.addMessage(FACEBOOK_ALREADY_EXISTS_MSG);
        }

        Account byVk = accountDAO.readByVkId(accountDTO.getVkId());
        if (byVk != null && !byVk.getId().equals(accountDTO.getId())) {
            errorDTO.addMessage(VK_ALREADY_EXISTS_MSG);
        }

        if (!CommonValidator.isNameValid(accountDTO.getFirstName())) {
            errorDTO.addMessage(INVALID_FIRST_NAME_MSG);
        }

        if (!CommonValidator.isNameValid(accountDTO.getSecondName())) {
            errorDTO.addMessage(INVALID_SECOND_NAME_MSG);
        }

        if (accountDTO.getScheduleNotification() == null) {
            errorDTO.addMessage(INVALID_SCHEDULE_NOT_MSG);
        }

        if (accountDTO.getSettingsNotification() == null) {
            errorDTO.addMessage(INVALID_SETTINGS_NOT_MSG);
        }

        if (accountDTO.getEmail() == null &&
                accountDTO.getPhoneNumber() == null &&
                accountDTO.getFacebookId() == null &&
                accountDTO.getVkId() == null) {
            errorDTO.addMessage(IDENTIFIER_REQ_MSG);
        }
        return errorDTO.getMessages().isEmpty();
    }

    private boolean checkNewAccountFields(PrivateNewAccountDTO accountDTO, ErrorDTO errorDTO) {
        if (LoginValidator.isEmailValid(accountDTO.getLogin())) {
            if (accountDAO.readByEmail(accountDTO.getLogin()) != null) {
                errorDTO.addMessage(EMAIL_ALREADY_EXISTS_MSG);
            }
        }
        else if (LoginValidator.isPhoneNumberValid(accountDTO.getLogin())) {
            if (accountDAO.readByPhoneNumber(new Long(accountDTO.getLogin())) != null) {
                errorDTO.addMessage(PHONE_ALREADY_EXISTS_MSG);
            }
        }
        else {
            errorDTO.addMessage(LOGIN_REQ_MSG);
        }

        if (!CommonValidator.isNameValid(accountDTO.getFirstName())) {
            errorDTO.addMessage(INVALID_FIRST_NAME_MSG);
        }

        if (!CommonValidator.isNameValid(accountDTO.getSecondName())) {
            errorDTO.addMessage(INVALID_SECOND_NAME_MSG);
        }

        if (!CommonValidator.isPasswordValid(accountDTO.getPassword())) {
            errorDTO.addMessage(String.format(INVALID_PASS_MSG,
                    CommonValidator.MIN_PASSWORD_LENGTH,
                    CommonValidator.MAX_PASSWORD_LENGTH));
        }

        return errorDTO.getMessages().isEmpty();
    }
}
