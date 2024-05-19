package com.digs.dig0.service;


import com.digs.dig0.dto.UserDTO;
import com.digs.dig0.model.Role;
import com.digs.dig0.model.Status;
import com.digs.dig0.model.User;
import com.digs.dig0.repository.TokenRepository;
import com.digs.dig0.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.tokenService = new TokenServiceImpl(tokenRepository, userRepository);
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user
     * And saves in database
     */
    @Override
    public User register(UserDTO registerRequest) {
        log.info("Inside Authentication Service: authenticating login attempt for: {}", registerRequest.getUsername());
        Collection<Role> roles = new HashSet<>();
        Role role = new Role("ROLE_USER");
        roles.add(role);

        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .phn(registerRequest.getPhn())
                .authority(roles)
                .isEnabled(false)
                .status(Status.NOT_ACTIVE)
                .build();

        log.info("Registered new user: {}", registerRequest.getUsername());
       return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void updateUser(Long id, String username, String name, String phn) {
        userRepository.update(username, name, phn, id);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        Optional<User> theUser = userRepository.findById(id);
        theUser.ifPresent(user -> tokenService.deleteUserToken(user.getId()));
        userRepository.deleteById(id);
    }
}