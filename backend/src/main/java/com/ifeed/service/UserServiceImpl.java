package com.ifeed.service;

import com.ifeed.mapper.UserMapper;
import com.ifeed.model.User;
import com.ifeed.model.dto.UserDTO;
import com.ifeed.model.dto.UserEditDTO;
import com.ifeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Our implementation of Spring Security's UserDetailService
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = userMapper;
    }

    @Override
    public List<UserDTO> loadAll() {
        return mapper.map(userRepository.findAll());
    }

    @Override
    public UserDTO findByUsername(String userName) {
        return mapper.map(userRepository.findByUserName(userName));
    }

    @Override
    public UserDTO find(long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return null;
        }
        return mapper.map(user);
    }

    @Override
    public UserDTO save(UserEditDTO user) {
        User entity = null;
        if (user.getId() != null) {
            entity = userRepository.findOne(user.getId());
        }

        if (entity == null) {
            entity = new User();
        }

        mapper.map(user, entity);

        // iff password was reset, overwrite it explicitly
        if (user.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            entity.setPassword(encodedPassword);
        }

        User savedUser = userRepository.save(entity);

        return mapper.map(savedUser);
    }


    @Override
    @Transactional(readOnly = true)
    public CurrentUser loadUserByUsername(String userName) {
        User user = findUser(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName + " does not exist");
        }

        if ("admin".equals(userName)) {
            return new CurrentUser(user, user.getPassword(), user.getId(), new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return new CurrentUser(user, user.getPassword(), user.getId());
        }
    }

    @Override
    public User getCurrentUser() {
        return findUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private User findUser(final String userName) {
        if (!ANONYMOUS_USER.equals(userName)) {
            User user = userRepository.findByUserName(userName);
            return user;
        }
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return getCurrentUser() != null;
    }

    public void updateLastLoginDate(String userName, Date date) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            return;
        }
        user.setLastLogin(date);
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String userName = ((UserDetails) event.getAuthentication().
                getPrincipal()).getUsername();

        updateLastLoginDate(userName, new Date());
    }
}
