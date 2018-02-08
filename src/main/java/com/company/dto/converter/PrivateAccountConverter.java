package com.company.dto.converter;

import com.company.dto.PrivateAccountDTO;
import com.company.model.Account;
import com.company.model.Group;

public class PrivateAccountConverter extends EntityConverter<Account, PrivateAccountDTO> {
    @Override
    public PrivateAccountDTO convert(Account entity) {
        return new PrivateAccountDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getSecondName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getGroup().getId()
        );
    }

    @Override
    public Account restore(PrivateAccountDTO dto) {
        Group groupStub = new Group();
        groupStub.setId(dto.getGroup_id());
        return new Account(
                STRING_STUB,
                dto.getFirstName(),
                dto.getSecondName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                STRING_STUB,
                groupStub
        );
    }
}
