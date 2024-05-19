package com.digs.dig0.password;


import com.digs.dig0.model.User;
import java.util.Optional;

/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */


public interface IPasswordResetTokenService {
    String validatePasswordResetToken(String theToken);

    Optional<User> findUserByPasswordResetToken(String theToken);

    void resetPassword(User theUser, String password);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
}