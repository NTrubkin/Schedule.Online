package com.company.service.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

/**
 * Важно помнить, что в приложении нет понятия username
 * firstName и SecondName не идентифицируют пользователя однозначно и могут быть изменены, поэтому использовать их нельзя
 * Поэтому username генерируется на основе id методом generateUsername()
 */
public class CustomUser extends User implements CustomUserDetails {
    private final int id;

    public CustomUser(int id, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(generateUsername(id), password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    private static String generateUsername(int id) {
        return "#" + id;
    }

    @Override
    public int getUserId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomUser that = (CustomUser) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
