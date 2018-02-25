package com.company.dto.converter;

import com.company.dto.PrivateAccountDTO;
import com.company.model.Account;
import com.company.model.Group;
import org.springframework.stereotype.Service;

@Service
public class PrivateAccountConverter extends EntityConverter<Account, PrivateAccountDTO> {
    @Override
    public PrivateAccountDTO convert(Account entity) {
        if (entity == null) return null;
        else return new PrivateAccountDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getSecondName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getGroup() == null ? null : entity.getGroup().getId(),
                entity.getSettingsNotification(),
                entity.getScheduleNotidication(),
                entity.getFacebookId(),
                entity.getGoogleId(),
                entity.getVkId());
    }

    @Override
    public Account restore(PrivateAccountDTO dto) {
        if (dto == null) {
            return null;
        }
        else {
            Group groupStub = new Group();
            groupStub.setId(dto.getGroupId());
            return new Account(
                    dto.getId(),
                    dto.getFirstName(),
                    dto.getSecondName(),
                    dto.getEmail(),
                    dto.getPhoneNumber(),
                    STRING_STUB,
                    groupStub,
                    dto.getSettingsNotification(),
                    dto.getScheduleNotification(),
                    dto.getFacebookId(),
                    dto.getGoogleId(),
                    dto.getVkId());
        }
    }
}
