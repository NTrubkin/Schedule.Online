package com.company.dto.converter;

import com.company.dto.AccountDTO;
import com.company.model.Account;
import com.company.model.Group;
import org.springframework.stereotype.Service;

@Service
public class AccountConverter extends EntityConverter<Account, AccountDTO> {
    private static final Long LONG_STUB = 0L;
    private static final Group GROUP_STUB = new Group();

    @Override
    public AccountDTO convert(Account entity) {
        return new AccountDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getSecondName()
        );
    }

    @Override
    public Account restore(AccountDTO dto) {
        return new Account(
                STRING_STUB,
                dto.getFirstName(),
                dto.getSecondName(),
                STRING_STUB,
                LONG_STUB,
                STRING_STUB,
                GROUP_STUB
        );
    }
}
