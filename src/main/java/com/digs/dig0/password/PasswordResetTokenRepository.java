package com.digs.dig0.password;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String theToken);
}