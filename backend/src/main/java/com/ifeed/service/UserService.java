package com.ifeed.service;

import com.ifeed.model.User;
import com.ifeed.model.dto.UserDTO;
import com.ifeed.model.dto.UserEditDTO;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {
    String ANONYMOUS_USER = "anonymousUser";
    List<UserDTO> loadAll();
    UserDTO findByUsername(String userName);
    UserDTO find(long id);
    UserDTO save(UserEditDTO user);

    CurrentUser loadUserByUsername(String userName);
    User getCurrentUser();
    boolean isAuthenticated();


}