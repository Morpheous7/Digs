package com.digs.dig0.service;

import com.digs.dig0.model.Status;
import com.digs.dig0.model.Token;
import com.digs.dig0.model.User;
import com.digs.dig0.repository.TokenRepository;
import com.digs.dig0.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;


/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private CustomUserDetailsServices userDetailsService ;

    public TokenServiceImpl(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository =userRepository;
        this.tokenRepository = tokenRepository;
        this.userDetailsService = new CustomUserDetailsServices(userRepository);
    }
    @Autowired
    public TokenServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, CustomUserDetailsServices userDetailsService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
    }
    @Override
    public String validateToken(String token) {
        Optional<Token> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()){
            return "INVALID";
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.get().getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "EXPIRED";
        }
        user.setEnabled(true);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return "VALID";
    }
    public boolean validateTokens(String token) {
        Optional<Token> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()){
            return false;
        }
        User user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.get().getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return false;
        }
        user.setEnabled(true);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return true;
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        var Token = new Token(token, user);
        tokenRepository.save(Token);
    }
    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public Authentication getAuthentication(String token) {
        String username = token;
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }


    public Optional<String> getTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = Objects.requireNonNullElse(request.getCookies(), new Cookie[]{});
        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    @Override
    public void deleteUserToken(Long id) {
        tokenRepository.deleteByUserId(id);
    }


}