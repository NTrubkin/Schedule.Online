package com.company.service.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface IdentifierAccountService {
    UserDetails loadUser(IdType idType, String id);
}
