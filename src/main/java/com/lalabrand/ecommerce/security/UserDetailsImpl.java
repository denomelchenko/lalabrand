package com.lalabrand.ecommerce.security;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.role.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl extends User implements UserDetails {
    private final String password;
    private final String email;
    @Getter
    private final String id;
    @Getter
    private final Integer passwordVersion;
    Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.id = user.getId();
        this.passwordVersion = user.getPasswordVersion();
        List<GrantedAuthority> auths = new ArrayList<>();

        for (UserRole role : user.getUserRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
