package com.lalabrand.ecommerce.auth;

import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.role.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl extends User implements UserDetails {
    private final String password;
    Collection<? extends GrantedAuthority> authorities;
    private final String username;

    public UserDetailsImpl(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
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
        return username;
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
