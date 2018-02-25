package com.company.dto.converter;

import com.company.dto.PrivateNewAccountDTO;
import com.company.model.Account;
import com.company.util.HashGenerator;
import com.company.util.LoginValidator;
import org.springframework.stereotype.Service;

@Service
public class PrivateNewAccountConverter extends EntityConverter<Account, PrivateNewAccountDTO> {
    @Override
    public PrivateNewAccountDTO convert(Account entity) {
        throw new UnsupportedOperationException("PrivateNewAccountDTO can not be converted");
    }

    @Override
    public Account restore(PrivateNewAccountDTO dto) {
        if (dto == null) {
            return null;
        }
        else {
            return new Account(
                    dto.getFirstName(),
                    dto.getSecondName(),
                    LoginValidator.isEmailValid(dto.getLogin()) ? dto.getLogin() : STRING_STUB,
                    LoginValidator.isPhoneNumberValid(dto.getLogin()) ? new Long(dto.getLogin()) : 0L,
                    HashGenerator.generateSHA1(dto.getPassword()),
                    null,
                    true,
                    true,
                    null,
                    null,
                    null
            );
        }
    }
}
