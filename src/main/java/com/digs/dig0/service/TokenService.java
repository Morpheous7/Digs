package com.digs.dig0.service;

import com.digs.dig0.model.Token;
import com.digs.dig0.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */

public interface TokenService {
    String validateToken(String token);
    void saveVerificationTokenForUser(User user, String token);
    Optional<Token> findByToken(String token);
    Authentication getAuthentication(String token);

    void deleteUserToken(Long id);
}