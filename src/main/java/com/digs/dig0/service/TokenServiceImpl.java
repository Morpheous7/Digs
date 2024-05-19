package com.digs.dig0.service;

import com.digs.dig0.model.Status;
import com.digs.dig0.model.Token;
import com.digs.dig0.model.User;
import com.digs.dig0.repository.TokenRepository;
import com.digs.dig0.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Optional;


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
    public void deleteUserToken(Long id) {
        tokenRepository.deleteByUserId(id);
    }
}