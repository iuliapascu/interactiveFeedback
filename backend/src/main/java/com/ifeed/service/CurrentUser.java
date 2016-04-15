package com.ifeed.service;

import com.ifeed.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final Long userId;
    private final String role;

    public CurrentUser(User user, String password, Long id, GrantedAuthority... authorities) {
        super(user.getUserName(), password, user.getRole() != null ? AuthorityUtils.createAuthorityList(user.getRole().toString()) : new ArrayList<>());
        this.userId = id;
        this.role = user.getRole() != null ? user.getRole().toString() : "";
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
