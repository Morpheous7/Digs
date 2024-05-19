package com.digs.dig0.repository;


import com.digs.dig0.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Copyright to Digs LLC
 *
 * @author Ike Kennedy
 */


public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    void deleteByUserId(Long id);
}