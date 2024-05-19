package com.digs.dig0.service;
import com.digs.dig0.dto.UserDTO;
import com.digs.dig0.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Service
public interface UserService  {
    List<User> getAllUsers();
    Optional<User> findByUsername(String username);
    Optional<User> save(User user);

    Optional<User> findById(Long id);

    void updateUser(Long id, String username, String name, String phn);

    void deleteUser(Long id);

    User register(UserDTO userDto);
}